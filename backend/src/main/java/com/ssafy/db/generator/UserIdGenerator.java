package com.ssafy.db.generator;

import org.hibernate.HibernateException;
import org.hibernate.MappingException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.Configurable;
import org.hibernate.id.IdentifierGenerator;
import org.hibernate.internal.util.config.ConfigurationHelper;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.type.Type;

import java.io.Serializable;
import java.util.Properties;

public class UserIdGenerator implements IdentifierGenerator {

    // ID 생성 처리 메소드
    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException {
        // 예시1> P1627954354252
        // 예시2> P1627954355374
        String str = "P" + System.currentTimeMillis();

        return str;
    }

}
