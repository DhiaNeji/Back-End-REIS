package Reis.PFE.Entities;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Objects;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.type.IntegerType;
import org.hibernate.type.StringType;
import org.hibernate.type.Type;
import org.hibernate.usertype.CompositeUserType;

public class AddressType implements CompositeUserType {
	 
    @Override
    public String[] getPropertyNames() {
        return new String[] { "addressLine1", "addressLine2", 
          "city", "country", "zipcode" };
    }
 
    @Override
    public Type[] getPropertyTypes() {
        return new Type[] { StringType.INSTANCE, 
          StringType.INSTANCE, 
          StringType.INSTANCE, 
          StringType.INSTANCE, 
          StringType.INSTANCE };
    }

	@Override
	public Object getPropertyValue(Object component, int property) throws HibernateException {

	    Address empAdd = (Address) component;
	 
	    switch (property) {
	    case 0:
	        return empAdd.getAdressLine1();
	    case 1:
	        return empAdd.getAdressLine2();
	    case 2:
	        return empAdd.getCity();
	    case 3:
	        return empAdd.getCoutry();
	    case 4:
	        return empAdd.getZipcode();
	    }
	 
	    throw new IllegalArgumentException(property + " is an invalid property index for class type "
	      + component.getClass().getName());
	}

	@Override
	public void setPropertyValue(Object component, int property, Object value) throws HibernateException {
		Address empAdd = (Address) component;
		 
	    switch (property) {
	    case 0:
	         empAdd.getAdressLine1();
	    case 1:
	         empAdd.getAdressLine2();
	    case 2:
	         empAdd.getCity();
	    case 3:
	         empAdd.getCoutry();
	    case 4:
	         empAdd.getZipcode();
	    }
	}

	@Override
	public Class returnedClass() {
		return Address.class;
	}

	@Override
	public boolean equals(Object x, Object y) throws HibernateException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int hashCode(Object x) throws HibernateException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Object nullSafeGet(ResultSet rs, String[] names, SharedSessionContractImplementor session, Object owner)
			throws HibernateException, SQLException {
		if(rs==null)
			return null;
		String adressLine1=rs.getString(names[0]);
		String adressLine2=rs.getString(names[1]);
		String city=rs.getString(names[2]);
		String country=rs.getString(names[3]);
		String zipCode=rs.getString(names[4]);
		return new Address(adressLine1,adressLine2,city,country,zipCode);
	}

	@Override
	public void nullSafeSet(PreparedStatement st, Object value, int index, SharedSessionContractImplementor session)
			throws HibernateException, SQLException {
		   if (Objects.isNull(value)) {
		        st.setNull(index, Types.INTEGER);
		    } else {
		        Address address = (Address) value;
		        st.setString(index,address.getAdressLine1());
		        st.setString(index+1,address.getAdressLine2());
		        st.setString(index+2,address.getCity());
		        st.setString(index+3,address.getCoutry());
		        st.setString(index+4,address.getZipcode());
		    }
		
	}

	@Override
	public Object deepCopy(Object value) throws HibernateException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isMutable() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Serializable disassemble(Object value, SharedSessionContractImplementor session) throws HibernateException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object assemble(Serializable cached, SharedSessionContractImplementor session, Object owner)
			throws HibernateException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object replace(Object original, Object target, SharedSessionContractImplementor session, Object owner)
			throws HibernateException {
		// TODO Auto-generated method stub
		return null;
	}
 
    // other methods
}