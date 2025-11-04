package com.me.cinemaapp.entity.idgenerator;


import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;
import org.hibernate.id.UUIDGenerator;

import java.util.UUID;

public class CustomUUIDGenerator implements IdentifierGenerator {
    @Override
    public Object generate(SharedSessionContractImplementor session, Object object) {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
