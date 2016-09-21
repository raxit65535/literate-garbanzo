/**
 * Autogenerated by Avro
 *
 * DO NOT EDIT DIRECTLY
 */
package com.drfloob.insight.pulse.venturi.schema.root;

import org.apache.avro.specific.SpecificData;

@SuppressWarnings("all")
@org.apache.avro.specific.AvroGenerated
public class Org extends org.apache.avro.specific.SpecificRecordBase implements org.apache.avro.specific.SpecificRecord {
  private static final long serialVersionUID = -7141596490110516142L;
  public static final org.apache.avro.Schema SCHEMA$ = new org.apache.avro.Schema.Parser().parse("{\"type\":\"record\",\"name\":\"Org\",\"namespace\":\"com.drfloob.insight.pulse.schema.root\",\"fields\":[{\"name\":\"id\",\"type\":[\"long\",\"null\"]},{\"name\":\"login\",\"type\":[\"string\",\"null\"]},{\"name\":\"gravatar_id\",\"type\":[\"string\",\"null\"]},{\"name\":\"avatar_url\",\"type\":[\"string\",\"null\"]},{\"name\":\"url\",\"type\":[\"string\",\"null\"]}]}");
  public static org.apache.avro.Schema getClassSchema() { return SCHEMA$; }
  @Deprecated public java.lang.Long id;
  @Deprecated public java.lang.CharSequence login;
  @Deprecated public java.lang.CharSequence gravatar_id;
  @Deprecated public java.lang.CharSequence avatar_url;
  @Deprecated public java.lang.CharSequence url;

  /**
   * Default constructor.  Note that this does not initialize fields
   * to their default values from the schema.  If that is desired then
   * one should use <code>newBuilder()</code>.
   */
  public Org() {}

  /**
   * All-args constructor.
   * @param id The new value for id
   * @param login The new value for login
   * @param gravatar_id The new value for gravatar_id
   * @param avatar_url The new value for avatar_url
   * @param url The new value for url
   */
  public Org(java.lang.Long id, java.lang.CharSequence login, java.lang.CharSequence gravatar_id, java.lang.CharSequence avatar_url, java.lang.CharSequence url) {
    this.id = id;
    this.login = login;
    this.gravatar_id = gravatar_id;
    this.avatar_url = avatar_url;
    this.url = url;
  }

  public org.apache.avro.Schema getSchema() { return SCHEMA$; }
  // Used by DatumWriter.  Applications should not call.
  public java.lang.Object get(int field$) {
    switch (field$) {
    case 0: return id;
    case 1: return login;
    case 2: return gravatar_id;
    case 3: return avatar_url;
    case 4: return url;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }

  // Used by DatumReader.  Applications should not call.
  @SuppressWarnings(value="unchecked")
  public void put(int field$, java.lang.Object value$) {
    switch (field$) {
    case 0: id = (java.lang.Long)value$; break;
    case 1: login = (java.lang.CharSequence)value$; break;
    case 2: gravatar_id = (java.lang.CharSequence)value$; break;
    case 3: avatar_url = (java.lang.CharSequence)value$; break;
    case 4: url = (java.lang.CharSequence)value$; break;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }

  /**
   * Gets the value of the 'id' field.
   * @return The value of the 'id' field.
   */
  public java.lang.Long getId() {
    return id;
  }

  /**
   * Sets the value of the 'id' field.
   * @param value the value to set.
   */
  public void setId(java.lang.Long value) {
    this.id = value;
  }

  /**
   * Gets the value of the 'login' field.
   * @return The value of the 'login' field.
   */
  public java.lang.CharSequence getLogin() {
    return login;
  }

  /**
   * Sets the value of the 'login' field.
   * @param value the value to set.
   */
  public void setLogin(java.lang.CharSequence value) {
    this.login = value;
  }

  /**
   * Gets the value of the 'gravatar_id' field.
   * @return The value of the 'gravatar_id' field.
   */
  public java.lang.CharSequence getGravatarId() {
    return gravatar_id;
  }

  /**
   * Sets the value of the 'gravatar_id' field.
   * @param value the value to set.
   */
  public void setGravatarId(java.lang.CharSequence value) {
    this.gravatar_id = value;
  }

  /**
   * Gets the value of the 'avatar_url' field.
   * @return The value of the 'avatar_url' field.
   */
  public java.lang.CharSequence getAvatarUrl() {
    return avatar_url;
  }

  /**
   * Sets the value of the 'avatar_url' field.
   * @param value the value to set.
   */
  public void setAvatarUrl(java.lang.CharSequence value) {
    this.avatar_url = value;
  }

  /**
   * Gets the value of the 'url' field.
   * @return The value of the 'url' field.
   */
  public java.lang.CharSequence getUrl() {
    return url;
  }

  /**
   * Sets the value of the 'url' field.
   * @param value the value to set.
   */
  public void setUrl(java.lang.CharSequence value) {
    this.url = value;
  }

  /**
   * Creates a new Org RecordBuilder.
   * @return A new Org RecordBuilder
   */
  public static Org.Builder newBuilder() {
    return new Org.Builder();
  }

  /**
   * Creates a new Org RecordBuilder by copying an existing Builder.
   * @param other The existing builder to copy.
   * @return A new Org RecordBuilder
   */
  public static Org.Builder newBuilder(Org.Builder other) {
    return new Org.Builder(other);
  }

  /**
   * Creates a new Org RecordBuilder by copying an existing Org instance.
   * @param other The existing instance to copy.
   * @return A new Org RecordBuilder
   */
  public static Org.Builder newBuilder(Org other) {
    return new Org.Builder(other);
  }

  /**
   * RecordBuilder for Org instances.
   */
  public static class Builder extends org.apache.avro.specific.SpecificRecordBuilderBase<Org>
    implements org.apache.avro.data.RecordBuilder<Org> {

    private java.lang.Long id;
    private java.lang.CharSequence login;
    private java.lang.CharSequence gravatar_id;
    private java.lang.CharSequence avatar_url;
    private java.lang.CharSequence url;

    /** Creates a new Builder */
    private Builder() {
      super(SCHEMA$);
    }

    /**
     * Creates a Builder by copying an existing Builder.
     * @param other The existing Builder to copy.
     */
    private Builder(Org.Builder other) {
      super(other);
      if (isValidValue(fields()[0], other.id)) {
        this.id = data().deepCopy(fields()[0].schema(), other.id);
        fieldSetFlags()[0] = true;
      }
      if (isValidValue(fields()[1], other.login)) {
        this.login = data().deepCopy(fields()[1].schema(), other.login);
        fieldSetFlags()[1] = true;
      }
      if (isValidValue(fields()[2], other.gravatar_id)) {
        this.gravatar_id = data().deepCopy(fields()[2].schema(), other.gravatar_id);
        fieldSetFlags()[2] = true;
      }
      if (isValidValue(fields()[3], other.avatar_url)) {
        this.avatar_url = data().deepCopy(fields()[3].schema(), other.avatar_url);
        fieldSetFlags()[3] = true;
      }
      if (isValidValue(fields()[4], other.url)) {
        this.url = data().deepCopy(fields()[4].schema(), other.url);
        fieldSetFlags()[4] = true;
      }
    }

    /**
     * Creates a Builder by copying an existing Org instance
     * @param other The existing instance to copy.
     */
    private Builder(Org other) {
            super(SCHEMA$);
      if (isValidValue(fields()[0], other.id)) {
        this.id = data().deepCopy(fields()[0].schema(), other.id);
        fieldSetFlags()[0] = true;
      }
      if (isValidValue(fields()[1], other.login)) {
        this.login = data().deepCopy(fields()[1].schema(), other.login);
        fieldSetFlags()[1] = true;
      }
      if (isValidValue(fields()[2], other.gravatar_id)) {
        this.gravatar_id = data().deepCopy(fields()[2].schema(), other.gravatar_id);
        fieldSetFlags()[2] = true;
      }
      if (isValidValue(fields()[3], other.avatar_url)) {
        this.avatar_url = data().deepCopy(fields()[3].schema(), other.avatar_url);
        fieldSetFlags()[3] = true;
      }
      if (isValidValue(fields()[4], other.url)) {
        this.url = data().deepCopy(fields()[4].schema(), other.url);
        fieldSetFlags()[4] = true;
      }
    }

    /**
      * Gets the value of the 'id' field.
      * @return The value.
      */
    public java.lang.Long getId() {
      return id;
    }

    /**
      * Sets the value of the 'id' field.
      * @param value The value of 'id'.
      * @return This builder.
      */
    public Org.Builder setId(java.lang.Long value) {
      validate(fields()[0], value);
      this.id = value;
      fieldSetFlags()[0] = true;
      return this;
    }

    /**
      * Checks whether the 'id' field has been set.
      * @return True if the 'id' field has been set, false otherwise.
      */
    public boolean hasId() {
      return fieldSetFlags()[0];
    }


    /**
      * Clears the value of the 'id' field.
      * @return This builder.
      */
    public Org.Builder clearId() {
      id = null;
      fieldSetFlags()[0] = false;
      return this;
    }

    /**
      * Gets the value of the 'login' field.
      * @return The value.
      */
    public java.lang.CharSequence getLogin() {
      return login;
    }

    /**
      * Sets the value of the 'login' field.
      * @param value The value of 'login'.
      * @return This builder.
      */
    public Org.Builder setLogin(java.lang.CharSequence value) {
      validate(fields()[1], value);
      this.login = value;
      fieldSetFlags()[1] = true;
      return this;
    }

    /**
      * Checks whether the 'login' field has been set.
      * @return True if the 'login' field has been set, false otherwise.
      */
    public boolean hasLogin() {
      return fieldSetFlags()[1];
    }


    /**
      * Clears the value of the 'login' field.
      * @return This builder.
      */
    public Org.Builder clearLogin() {
      login = null;
      fieldSetFlags()[1] = false;
      return this;
    }

    /**
      * Gets the value of the 'gravatar_id' field.
      * @return The value.
      */
    public java.lang.CharSequence getGravatarId() {
      return gravatar_id;
    }

    /**
      * Sets the value of the 'gravatar_id' field.
      * @param value The value of 'gravatar_id'.
      * @return This builder.
      */
    public Org.Builder setGravatarId(java.lang.CharSequence value) {
      validate(fields()[2], value);
      this.gravatar_id = value;
      fieldSetFlags()[2] = true;
      return this;
    }

    /**
      * Checks whether the 'gravatar_id' field has been set.
      * @return True if the 'gravatar_id' field has been set, false otherwise.
      */
    public boolean hasGravatarId() {
      return fieldSetFlags()[2];
    }


    /**
      * Clears the value of the 'gravatar_id' field.
      * @return This builder.
      */
    public Org.Builder clearGravatarId() {
      gravatar_id = null;
      fieldSetFlags()[2] = false;
      return this;
    }

    /**
      * Gets the value of the 'avatar_url' field.
      * @return The value.
      */
    public java.lang.CharSequence getAvatarUrl() {
      return avatar_url;
    }

    /**
      * Sets the value of the 'avatar_url' field.
      * @param value The value of 'avatar_url'.
      * @return This builder.
      */
    public Org.Builder setAvatarUrl(java.lang.CharSequence value) {
      validate(fields()[3], value);
      this.avatar_url = value;
      fieldSetFlags()[3] = true;
      return this;
    }

    /**
      * Checks whether the 'avatar_url' field has been set.
      * @return True if the 'avatar_url' field has been set, false otherwise.
      */
    public boolean hasAvatarUrl() {
      return fieldSetFlags()[3];
    }


    /**
      * Clears the value of the 'avatar_url' field.
      * @return This builder.
      */
    public Org.Builder clearAvatarUrl() {
      avatar_url = null;
      fieldSetFlags()[3] = false;
      return this;
    }

    /**
      * Gets the value of the 'url' field.
      * @return The value.
      */
    public java.lang.CharSequence getUrl() {
      return url;
    }

    /**
      * Sets the value of the 'url' field.
      * @param value The value of 'url'.
      * @return This builder.
      */
    public Org.Builder setUrl(java.lang.CharSequence value) {
      validate(fields()[4], value);
      this.url = value;
      fieldSetFlags()[4] = true;
      return this;
    }

    /**
      * Checks whether the 'url' field has been set.
      * @return True if the 'url' field has been set, false otherwise.
      */
    public boolean hasUrl() {
      return fieldSetFlags()[4];
    }


    /**
      * Clears the value of the 'url' field.
      * @return This builder.
      */
    public Org.Builder clearUrl() {
      url = null;
      fieldSetFlags()[4] = false;
      return this;
    }

    public Org build() {
      try {
        Org record = new Org();
        record.id = fieldSetFlags()[0] ? this.id : (java.lang.Long) defaultValue(fields()[0]);
        record.login = fieldSetFlags()[1] ? this.login : (java.lang.CharSequence) defaultValue(fields()[1]);
        record.gravatar_id = fieldSetFlags()[2] ? this.gravatar_id : (java.lang.CharSequence) defaultValue(fields()[2]);
        record.avatar_url = fieldSetFlags()[3] ? this.avatar_url : (java.lang.CharSequence) defaultValue(fields()[3]);
        record.url = fieldSetFlags()[4] ? this.url : (java.lang.CharSequence) defaultValue(fields()[4]);
        return record;
      } catch (Exception e) {
        throw new org.apache.avro.AvroRuntimeException(e);
      }
    }
  }

  private static final org.apache.avro.io.DatumWriter
    WRITER$ = new org.apache.avro.specific.SpecificDatumWriter(SCHEMA$);

  @Override public void writeExternal(java.io.ObjectOutput out)
    throws java.io.IOException {
    WRITER$.write(this, SpecificData.getEncoder(out));
  }

  private static final org.apache.avro.io.DatumReader
    READER$ = new org.apache.avro.specific.SpecificDatumReader(SCHEMA$);

  @Override public void readExternal(java.io.ObjectInput in)
    throws java.io.IOException {
    READER$.read(this, SpecificData.getDecoder(in));
  }

}
