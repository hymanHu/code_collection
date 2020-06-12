package com.thornBird.base.json;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class FullDateJsonSerializer extends JsonSerializer<Date> {

	@Override
	public void serialize(Date value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
		SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM d HH:mm:ss yyyy", Locale.US);
        sdf.setTimeZone(TimeZone.getTimeZone("PST"));
        gen.writeString(sdf.format(value));
	}

}
