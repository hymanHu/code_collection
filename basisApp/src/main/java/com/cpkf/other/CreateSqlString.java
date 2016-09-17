package com.cpkf.other;

public class CreateSqlString {

	public static String PREFIX_FORWORD = "forward_";
	public static String PREFIX_BACKWORD = "backward_";
	public static String SUFFIX_EDGE = "_edges";
	public static String SUFFIX_ATTR = "_edge_attrs";

	public int shardStart = 1;
	public int shardEnd = 60;
	public int state = 0;
	public int minId = 0;
	public int maxId = 0;
	
	public void queryShardSql() {
		StringBuffer sb = new StringBuffer();
		sb.append("select count(*) from (\r\n");
		for (int i = shardStart; i <= shardEnd; i++) {
			sb.append(String.format("%s%s%s%s%s%s%s%s%s%s%s%s%s%s%s%s%s", "select source_id from ",
					PREFIX_FORWORD, i, SUFFIX_EDGE, " t", ((i - 1) * 4 + 1), " where t",
					((i - 1) * 4 + 1), ".state=", state, " and t", ((i - 1) * 4 + 1),
					".source_id BETWEEN ", minId, " AND ", maxId, " union all\r\n"));
			sb.append(String.format("%s%s%s%s%s%s%s%s%s%s%s%s%s%s%s%s%s", "select source_id from ",
					PREFIX_FORWORD, i, SUFFIX_ATTR, " t", ((i - 1) * 4 + 2), " where t",
					((i - 1) * 4 + 2), ".state=", state, " and t", ((i - 1) * 4 + 2),
					".source_id BETWEEN ", minId, " AND ", maxId, " union all\r\n"));
			sb.append(String.format("%s%s%s%s%s%s%s%s%s%s%s%s%s%s%s%s%s", "select source_id from ",
					PREFIX_BACKWORD, i, SUFFIX_EDGE, " t", ((i - 1) * 4 + 3), " where t",
					((i - 1) * 4 + 3), ".state=", state, " and t", ((i - 1) * 4 + 3),
					".source_id BETWEEN ", minId, " AND ", maxId, " union all\r\n"));
			if (i == shardEnd) {
				sb.append(String.format("%s%s%s%s%s%s%s%s%s%s%s%s%s%s%s%s%s",
						"select source_id from ", PREFIX_BACKWORD, i, SUFFIX_ATTR, " t",
						((i - 1) * 4 + 4), " where t", ((i - 1) * 4 + 4), ".state=", state,
						" and t", ((i - 1) * 4 + 4), ".source_id BETWEEN ", minId, " AND ",
						maxId, "\r\n"));
			} else {
				sb.append(String.format("%s%s%s%s%s%s%s%s%s%s%s%s%s%s%s%s%s",
						"select source_id from ", PREFIX_BACKWORD, i, SUFFIX_ATTR, " t",
						((i - 1) * 4 + 4), " where t", ((i - 1) * 4 + 4), ".state=", state,
						" and t", ((i - 1) * 4 + 4), ".source_id BETWEEN ", minId, " AND ",
						maxId, " union all\r\n"));
			}
		}
		sb.append(") t");
		System.out.println(sb.toString());
	}
	
	public void queryShardSql1() {
		StringBuffer sb = new StringBuffer();
		sb.append("select count(*) from (\r\n");
		for (int i = shardStart; i <= shardEnd; i++) {
			sb.append(String.format("%s%s%s%s%s%s%s%s%s%s%s", "select source_id from ",
					PREFIX_FORWORD, i, SUFFIX_EDGE, " t", ((i - 1) * 4 + 1),
					" where t", ((i - 1) * 4 + 1), ".state=", state,
					" union all\r\n"));
			sb.append(String.format("%s%s%s%s%s%s%s%s%s%s%s", "select source_id from ",
					PREFIX_FORWORD, i, SUFFIX_ATTR, " t", ((i - 1) * 4 + 2),
					" where t", ((i - 1) * 4 + 2), ".state=", state,
					" union all\r\n"));
			sb.append(String.format("%s%s%s%s%s%s%s%s%s%s%s", "select source_id from ",
					PREFIX_BACKWORD, i, SUFFIX_EDGE, " t", ((i - 1) * 4 + 3),
					" where t", ((i - 1) * 4 + 3), ".state=", state,
					" union all\r\n"));
			if (i == shardEnd) {
				sb.append(String.format("%s%s%s%s%s%s%s%s%s%s%s",
						"select source_id from ", PREFIX_BACKWORD, i, SUFFIX_ATTR,
						" t", ((i - 1) * 4 + 4), " where t", ((i - 1) * 4 + 4),
						".state=", state, "\r\n"));
			} else {
				sb.append(String.format("%s%s%s%s%s%s%s%s%s%s%s",
						"select source_id from ", PREFIX_BACKWORD, i, SUFFIX_ATTR,
						" t", ((i - 1) * 4 + 4), " where t", ((i - 1) * 4 + 4),
						".state=", state, " union all\r\n"));
			}
		}
		sb.append(") t");
		System.out.println(sb.toString());
	}

	public void deleteSql() {
		StringBuffer sb = new StringBuffer();
		for (int i = shardStart; i <= shardEnd; i++) {
			sb.append(String.format("%s%s%s%s%s%s%s", "DELETE FROM ", PREFIX_FORWORD,
					i, SUFFIX_EDGE, " WHERE state=", state, ";\r\n"));
			sb.append(String.format("%s%s%s%s%s%s%s", "DELETE FROM ", PREFIX_FORWORD,
					i, SUFFIX_ATTR, " WHERE state=", state, ";\r\n"));
			sb.append(String.format("%s%s%s%s%s%s%s", "DELETE FROM ", PREFIX_BACKWORD,
					i, SUFFIX_EDGE, " WHERE state=", state, ";\r\n"));
			sb.append(String.format("%s%s%s%s%s%s%s", "DELETE FROM ", PREFIX_BACKWORD,
					i, SUFFIX_ATTR, " WHERE state=", state, ";\r\n"));
		}
		System.out.println(sb.toString());
	}

	public static void main(String[] args) {
		CreateSqlString sql = new CreateSqlString();
//		sql.queryShardSql();
		sql.queryShardSql1();
//		sql.deleteSql();
	}

}
