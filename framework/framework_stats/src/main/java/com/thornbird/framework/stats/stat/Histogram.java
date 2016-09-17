package com.thornbird.framework.stats.stat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * stats 柱状图
 * 
 * @author hyman
 */
public class Histogram {

	/*
	 * The midpoint of each bucket is +/- 5% from the boundaries. (0..139).map {
	 * |n| (1.10526315 ** n).to_i + 1 }.uniq Bucket i is the range from
	 * BUCKET_OFFSETS(i-1) (inclusive) to BUCKET_OFFSETS(i) (exclusive). The
	 * last bucket (the "infinity" bucket) is from 1100858 to infinity.
	 */
	public static final int[] BUCKET_OFFSETS = new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 12, 13,
			14, 15, 17, 19, 21, 23, 25, 28, 31, 34, 37, 41, 45, 50, 55, 61, 67, 74, 82, 91, 100,
			111, 122, 135, 150, 165, 183, 202, 223, 246, 272, 301, 332, 367, 406, 449, 496, 548,
			606, 669, 740, 817, 903, 999, 1104, 1220, 1348, 1490, 1647, 1820, 2011, 2223, 2457,
			2716, 3001, 3317, 3666, 4052, 4479, 4950, 5471, 6047, 6684, 7387, 8165, 9024, 9974,
			11024, 12184, 13467, 14884, 16451, 18182, 20096, 22212, 24550, 27134, 29990, 33147,
			36636, 40492, 44754, 49465, 54672, 60427, 66787, 73818, 81588, 90176, 99668, 110160,
			121755, 134572, 148737, 164393, 181698, 200824, 221963, 245328, 271152, 299694, 331240,
			366108, 404645, 447240, 494317, 546351, 603861, 667426, 737681, 815331, 901156, 996014,
			1100858 };
	public static final int bucketOffsetSize = BUCKET_OFFSETS.length;

	public static int bucketIndex(int key) {
		return binarySearch(key);
	}

	private static int binarySearch(int[] array, int key, int low, int high) {
		if (low > high) {
			return low;
		} else {
			int mid = (low + high + 1) >> 1;
			int midValue = array[mid];
			if (midValue < key) {
				return binarySearch(array, key, mid + 1, high);
			} else if (midValue > key) {
				return binarySearch(array, key, low, mid - 1);
			} else {
				// exactly equal to this bucket's value. but the value is an
				// exclusive max, so bump
				// it up.
				return mid + 1;
			}
		}
	}

	public static int binarySearch(int key) {
		return binarySearch(BUCKET_OFFSETS, key, 0, BUCKET_OFFSETS.length - 1);
	}

	public static Histogram create(int... values) {
		Histogram h = new Histogram();
		for (int value : values) {
			h.add(value);
		}
		return h;
	}

	int numBuckets = Histogram.BUCKET_OFFSETS.length + 1;
	long[] buckets = new long[numBuckets];
	long count = 0L;
	long sum = 0L;

	/**
	 * Adds a value directly to a bucket in a histogram. Can be used for
	 * performance reasons when modifying the histogram object from within a
	 * synchronized block.
	 * 
	 * @param index
	 *            the index of the bucket. Should be obtained from a value by
	 *            calling Histogram.bucketIndex(n) on the value.
	 */
	void addToBucket(int index) {
		buckets[index] += 1;
		count += 1;
	}

	long add(int n) {
		addToBucket(Histogram.bucketIndex(n));
		sum += n;
		return count;
	}

	void clear() {
		for (int i = 0; i < numBuckets; i++) {
			buckets[i] = 0;
		}
		count = 0;
		sum = 0;
	}

	List<Long> get(boolean reset) {
		List<Long> rv = new ArrayList<Long>(buckets.length);
		for (long bucket : buckets) {
			rv.add(bucket);
		}
		if (reset) {
			clear();
		}
		return rv;
	}

	/**
	 * Percentile within 5%, but: 0 if no values Int.MaxValue if percentile is
	 * out of range
	 */
	int getPercentile(double percentile) {
		if (percentile == 0.0)
			return minimum();
		long total = 0L;
		int index = 0;
		while (total < percentile * count) {
			total += buckets[index];
			index += 1;
		}
		if (index == 0) {
			return 0;
		} else if (index - 1 >= Histogram.BUCKET_OFFSETS.length) {
			return Integer.MAX_VALUE;
		} else {
			return midpoint(index - 1);
		}
	}

	/**
	 * Maximum value within 5%, but: 0 if no values Int.MaxValue if any value is
	 * infinity
	 */
	int maximum() {
		if (buckets[buckets.length - 1] > 0) {
			// Infinity bucket has a value
			return Integer.MAX_VALUE;
		} else if (count == 0) {
			// No values
			return 0;
		} else {
			int index = Histogram.BUCKET_OFFSETS.length - 1;
			while (index >= 0 && buckets[index] == 0)

				return index -= 1;
			if (index < 0)
				return 0;
			else
				return midpoint(index);
		}
	}

	/**
	 * Minimum value within 5%, but: 0 if no values Int.MaxValue if all values
	 * are infinity
	 */
	int minimum() {
		if (count == 0) {
			return 0;
		} else {
			int index = 0;
			while (index < Histogram.BUCKET_OFFSETS.length && buckets[index] == 0)
				index += 1;
			if (index >= Histogram.BUCKET_OFFSETS.length)
				return Integer.MAX_VALUE;
			else
				return midpoint(index);
		}
	}

	// Get midpoint of bucket
	protected int midpoint(int index) {
		if (index == 0)
			return 0;
		else if (index - 1 >= Histogram.BUCKET_OFFSETS.length)
			return Integer.MAX_VALUE;
		else
			return (Histogram.BUCKET_OFFSETS[index - 1] + Histogram.BUCKET_OFFSETS[index] - 1) / 2;
	}

	void merge(Histogram other) {
		if (other.count > 0) {
			for (int i = 0; i < numBuckets; i++) {
				buckets[i] += other.buckets[i];
			}
			count += other.count;
			sum += other.sum;
		}
	}

	Histogram minus(Histogram other) {
		Histogram rv = new Histogram();
		rv.count = count - other.count;
		rv.sum = sum - other.sum;

		for (int i = 0; i < numBuckets; i++) {
			rv.buckets[i] = buckets[i] - other.buckets[i];
		}
		return rv;
	}

	/**
	 * Get an immutable snapshot of this histogram.
	 */
	// def apply(): Distribution = new Distribution(clone())
	public boolean equals(Object o) {
		if (o instanceof Histogram) {
			Histogram h = (Histogram) o;
			return h.count == count && h.sum == sum && Arrays.equals(h.buckets, buckets);
		}
		return false;
	}

	public String toString() {
		String s = "<Histogram count=" + count + " sum=" + sum;
		List<String> bucketStrings = new ArrayList<String>(buckets.length);
		for (int i = 0; i < buckets.length; i++) {
			String b = "";
			if (i < BUCKET_OFFSETS.length) {
				b += BUCKET_OFFSETS[i];
			} else {
				b += "inf";
			}
			b += "=";
			b += buckets[i];
			bucketStrings.add(b);
		}

		for (String bucketString : bucketStrings) {
			s += " ";
			s += bucketString + ", ";
		}
		s = s.substring(0, s.length() - 1);
		s += ">";
		return s;
	}

	@Override
	public Histogram clone() throws CloneNotSupportedException {
		super.clone();
		Histogram histogram = new Histogram();
		histogram.merge(this);
		return histogram;
	}

	public static void main(String[] args) {
		Histogram h = new Histogram();
		Random r = new Random();
		for (int i = 0; i < 10; i++) {
			h.add(r.nextInt());
		}

		System.out.println(h);

	}

}
