/*
 * Copyright 2019 NAVER Corp.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.navercorp.pinpoint.profiler.context.proto;

import com.google.protobuf.ByteString;
import com.navercorp.pinpoint.common.util.IntBooleanIntBooleanValue;
import com.navercorp.pinpoint.common.util.IntStringStringValue;
import com.navercorp.pinpoint.common.util.IntStringValue;
import com.navercorp.pinpoint.common.util.LongIntIntByteByteStringValue;
import com.navercorp.pinpoint.common.util.StringStringValue;
import com.navercorp.pinpoint.common.util.StringUtils;
import com.navercorp.pinpoint.grpc.trace.PAnnotationValue;
import com.navercorp.pinpoint.grpc.trace.PIntBooleanIntBooleanValue;
import com.navercorp.pinpoint.grpc.trace.PIntStringStringValue;
import com.navercorp.pinpoint.grpc.trace.PIntStringValue;
import com.navercorp.pinpoint.grpc.trace.PLongIntIntByteByteStringValue;
import com.navercorp.pinpoint.grpc.trace.PStringStringValue;
import org.apache.thrift.TBase;

/**
 * Not thread safe
 * @author Woonduk Kang(emeroad)
 */
public class AnnotationValueProtoMapper {

    private final PAnnotationValue.Builder annotationBuilder = PAnnotationValue.newBuilder();

    public PAnnotationValue buildPAnnotationValue(Object value) {
        if (value == null) {
            return null;
        }
        if (value instanceof String) {
            PAnnotationValue.Builder builder = getAnnotationBuilder();
            builder.setStringValue((String) value);
            return builder.build();
        }
        if (value instanceof Integer) {
            PAnnotationValue.Builder builder = getAnnotationBuilder();
            builder.setIntValue((Integer) value);
            return builder.build();
        }
        if (value instanceof Long) {
            PAnnotationValue.Builder builder = getAnnotationBuilder();
            builder.setLongValue((Long) value);
            return builder.build();
        }
        if (value instanceof Boolean) {
            PAnnotationValue.Builder builder = getAnnotationBuilder();
            builder.setBoolValue((Boolean) value);
            return builder.build();
        }
        if (value instanceof Byte) {
            PAnnotationValue.Builder builder = getAnnotationBuilder();
            builder.setByteValue((Byte) value);
            return builder.build();
        }
        if (value instanceof Float) {
            PAnnotationValue.Builder builder = getAnnotationBuilder();
            // thrift does not contain "float" type
            builder.setDoubleValue((Float) value);
            return builder.build();
        }
        if (value instanceof Double) {
            PAnnotationValue.Builder builder = getAnnotationBuilder();
            builder.setDoubleValue((Double) value);
            return builder.build();
        }
        if (value instanceof byte[]) {
            PAnnotationValue.Builder builder = getAnnotationBuilder();
            builder.setBinaryValue(ByteString.copyFrom((byte[]) value));
            return builder.build();
        }
        if (value instanceof Short) {
            PAnnotationValue.Builder builder = getAnnotationBuilder();
            builder.setShortValue((Short) value);
            return builder.build();
        }
        if (value instanceof IntStringValue) {
            final IntStringValue v = (IntStringValue) value;
            PIntStringValue pIntStringValue = newIntStringValue(v);

            PAnnotationValue.Builder builder = getAnnotationBuilder();
            builder.setIntStringValue(pIntStringValue);

            return builder.build();
        }
        if (value instanceof StringStringValue) {
            final StringStringValue v = (StringStringValue) value;
            PStringStringValue pStringStringValue = newStringStringValue(v);

            PAnnotationValue.Builder builder = getAnnotationBuilder();
            builder.setStringStringValue(pStringStringValue);
            return builder.build();
        }
        if (value instanceof IntStringStringValue) {
            final IntStringStringValue v = (IntStringStringValue) value;
            final PIntStringStringValue pIntStringStringValue = newIntStringStringValue(v);
            PAnnotationValue.Builder builder = getAnnotationBuilder();
            builder.setIntStringStringValue(pIntStringStringValue);
            return builder.build();
        }
        if (value instanceof LongIntIntByteByteStringValue) {
            final LongIntIntByteByteStringValue v = (LongIntIntByteByteStringValue) value;
            final PLongIntIntByteByteStringValue pValue = newLongIntIntByteByteStringValue(v);

            PAnnotationValue.Builder builder = getAnnotationBuilder();
            builder.setLongIntIntByteByteStringValue(pValue);
            return builder.build();
        }
        if (value instanceof IntBooleanIntBooleanValue) {
            final IntBooleanIntBooleanValue v = (IntBooleanIntBooleanValue) value;
            final PIntBooleanIntBooleanValue pValue = newIntBooleanIntBooleanValue(v);
            PAnnotationValue.Builder builder = getAnnotationBuilder();
            builder.setIntBooleanIntBooleanValue(pValue);
            return builder.build();
        }
        if (value instanceof TBase) {
            throw new IllegalArgumentException("TBase not supported. Class:" + value.getClass());
        }
        String str = StringUtils.abbreviate(value.toString());
        PAnnotationValue.Builder builder = getAnnotationBuilder();
        builder.setStringValue(str);
        return builder.build();
    }

    private static PIntBooleanIntBooleanValue newIntBooleanIntBooleanValue(IntBooleanIntBooleanValue v) {
        PIntBooleanIntBooleanValue.Builder builder = PIntBooleanIntBooleanValue.newBuilder();
        builder.setIntValue1(v.getIntValue1());
        builder.setBoolValue1(v.isBooleanValue1());
        builder.setIntValue2(v.getIntValue2());
        builder.setBoolValue2(v.isBooleanValue2());
        return builder.build();
    }

    private static PLongIntIntByteByteStringValue newLongIntIntByteByteStringValue(LongIntIntByteByteStringValue v) {
        final PLongIntIntByteByteStringValue.Builder builder = PLongIntIntByteByteStringValue.newBuilder();
        builder.setLongValue(v.getLongValue());
        builder.setLongValue(v.getIntValue1());
        if (v.getIntValue2() != -1) {
            builder.setIntValue2(v.getIntValue2());
        }
        if (v.getByteValue1() != -1) {
            builder.setByteValue1(v.getByteValue1());
        }
        if (v.getByteValue2() != -1) {
            builder.setByteValue2(v.getByteValue2());
        }
        if (v.getStringValue() != null) {
            builder.setStringValue(v.getStringValue());
        }
        return builder.build();
    }

    private static PIntStringStringValue newIntStringStringValue(IntStringStringValue v) {
        final PIntStringStringValue.Builder builder = PIntStringStringValue.newBuilder();
        builder.setIntValue(v.getIntValue());
        if (v.getStringValue1() != null) {
            builder.setStringValue1(v.getStringValue1());
        }
        if (v.getStringValue2() != null) {
            builder.setStringValue2(v.getStringValue2());
        }
        return builder.build();
    }

    private static PIntStringValue newIntStringValue(IntStringValue v) {
        PIntStringValue.Builder valueBuilder = PIntStringValue.newBuilder();
        valueBuilder.setIntValue(v.getIntValue());
        if (v.getStringValue() != null) {
            valueBuilder.setStringValue(v.getStringValue());
        }
        return valueBuilder.build();
    }

    private static PStringStringValue newStringStringValue(StringStringValue v) {
        PStringStringValue.Builder builder = PStringStringValue.newBuilder();
        builder.setStringValue1(v.getStringValue1());

        final String stringValue2 = v.getStringValue2();
        if (stringValue2 != null) {
            builder.setStringValue2(stringValue2);
        }
        return builder.build();
    }

    private PAnnotationValue.Builder getAnnotationBuilder() {
        this.annotationBuilder.clear();
        return annotationBuilder;
    }
}
