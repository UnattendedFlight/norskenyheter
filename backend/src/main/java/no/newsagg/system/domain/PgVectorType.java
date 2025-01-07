package no.newsagg.system.domain;

import java.io.Serializable;
import java.sql.Array;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.usertype.UserType;

public class PgVectorType implements UserType<double[]> {

  @Override
  public int getSqlType() {
    return java.sql.Types.ARRAY;
  }

  @Override
  public Class<double[]> returnedClass() {
    return double[].class;
  }

  @Override
  public boolean equals(double[] x, double[] y) {
    if (x == y) return true;
    if (x == null || y == null) return false;
    if (x.length != y.length) return false;
    for (int i = 0; i < x.length; i++) {
      if (x[i] != y[i]) return false;
    }
    return true;
  }

  @Override
  public int hashCode(double[] x) {
    if (x == null) return 0;
    int result = 1;
    for (double element : x) {
      long bits = Double.doubleToLongBits(element);
      result = 31 * result + (int)(bits ^ (bits >>> 32));
    }
    return result;
  }

  @Override
  public double[] nullSafeGet(ResultSet rs, int position, SharedSessionContractImplementor session, Object owner)
      throws SQLException {
    Array array = rs.getArray(position);
    return array == null ? null : (double[]) array.getArray();
  }

  @Override
  public void nullSafeSet(PreparedStatement st, double[] value, int index, SharedSessionContractImplementor session)
      throws SQLException {
    if (value == null) {
      st.setNull(index, getSqlType());
    } else {
      Array array = session.getJdbcConnectionAccess()
          .obtainConnection()
          .createArrayOf("float8", toObjectArray(value));
      st.setArray(index, array);
    }
  }

  private Object[] toObjectArray(double[] array) {
    Double[] result = new Double[array.length];
    for (int i = 0; i < array.length; i++) {
      result[i] = array[i];
    }
    return result;
  }

  @Override
  public double[] deepCopy(double[] value) {
    if (value == null) return null;
    return value.clone();
  }

  @Override
  public boolean isMutable() {
    return true;
  }

  @Override
  public Serializable disassemble(double[] doubles) {
    if (doubles == null) {
      return null;
    }
    return doubles.clone();
  }

  @Override
  public double[] assemble(Serializable serializable, Object o) {
    if (serializable == null) {
      return null;
    }
    return ((double[]) serializable).clone();
  }
}