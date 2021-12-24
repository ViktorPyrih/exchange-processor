package ua.edu.cdu.vu.exchangeprocessor.entity.type;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.type.EnumType;
import ua.edu.cdu.vu.exchangeprocessor.dto.business.TransactionType;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

public class PostgreSQLEnum extends EnumType<TransactionType> {

    @Override
    public void nullSafeSet(PreparedStatement st, Object value, int index, SharedSessionContractImplementor session)
            throws HibernateException, SQLException {
        if (value == null) {
            st.setNull(index, Types.NULL);
        } else {
            st.setObject(index, value.toString(), Types.OTHER);
        }
    }
}
