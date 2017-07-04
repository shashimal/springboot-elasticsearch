package com.duleendra.model;

/**
 * Search Fields
 *
 *
 * @author Duleendra Shashimal
 *
 */
public class SearchField {

    /*
    * Elasticsearch Index
    *
    */
    public static String INDEX_NAME = "tremor_video";

    /*
    * Elasticsearch Types
    *
    */
    public static String TYPE_USER = "user";

    /*
    * Main fields for advanced search options
    *
    */
    public static String FIRST_NAME = "firstName";
    public static String LAST_NAME = "lastName";
    public static String BIO_DATA = "bioData";
    public static String SEARCH_SUGGEST = "searchSuggest";
    public static String ADMIN_JOB_RESPONSIBILITY = "adminJobResponsibility";

    /*
    * Suggestion name for SuggestionBuilder API
    *
    */
    public static String NAME_SUGGEST = "name_suggest";

}
