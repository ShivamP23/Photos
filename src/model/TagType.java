package model;

import java.io.Serializable;

/**
 * Represents a type of tags which will add to photo.
 *
 * @author Shivam Patel and Neel Patel
 */
public class TagType implements Serializable {

    private static final long serialVersionUID = 1L;
    private String name;
    private boolean single;

    /**
     * Represents a single tag value in the tag type.
     */
    public class Tag implements Serializable {

        private static final long serialVersionUID = 2L;
        private String tagVal;

        /**
         * Constructor for creating new tag value.
         *
         * @param val value of the tag
         */
        public Tag(String val) {
            this.tagVal = val;
        }

        /**
         * Method for returning the tag value.
         *
         * @return tag value
         */
        public String getTagValue() {
            return tagVal;
        }

        /**
         * Method for setting the new tag value.
         *
         * @param tagValue new tag value
         */
        public void setTagValue(String tagValue) {
            this.tagVal = tagValue;
        }

        /**
         * Return tag value in string format.
         *
         * @return string value of the tag.
         */
        public String toString() {
            return TagType.this.toString() + " : " + this.tagVal;
        }
    }

    /**
     * Constructor for creating new tag type.
     *
     * @param name name of the tag type.
     */
    public TagType(String name) {
        this.name = name;
    }

    /**
     * Constructor for creating a new Tag Type object with the specified name
     * and singularity.
     *
     * @param name the name of the tag type
     * @param single whether the tag type is singular or plural
     */
    public TagType(String name, Boolean single) {
        this.setName(name);
        this.single = single;
    }

    /**
     * Method for getting name of the tag type.
     *
     * @return name of the tag type
     */
    public String getName() {
        return name;
    }

    /**
     * Method for setting name of the tag type.
     *
     * @param typeName new name of the tag type.
     */
    public void setName(String typeName) {
        this.name = typeName;
    }

    /**
     * Returns Tag type in string format.
     *
     * @return string value of tag type
     */
    public String toString() {
        return this.name;
    }

    /**
     * Method for checking the tag type is single or plural.
     *
     * @return true if the tag type is singular, false if it is plural
     */
    public boolean isSingle() {
        return single;
    }

    /**
     * Method for setting the tag type is singular or plural.
     *
     * @param single true if the tag type is singular, false if it is plural
     */
    public void setSingle(boolean single) {
        this.single = single;
    }

}
