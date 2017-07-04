package com.duleendra.repository;

import com.duleendra.model.SearchField;
import com.duleendra.model.User;
import org.elasticsearch.action.suggest.SuggestRequestBuilder;
import org.elasticsearch.action.suggest.SuggestResponse;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.elasticsearch.search.suggest.Suggest;
import org.elasticsearch.search.suggest.completion.CompletionSuggestionBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/** Implementation of custom user repository functions
 *
 * @author Duleendra Shashimal
 */

@Repository("userRepositoryCustom")
public class UserRepositoryCustomImpl implements UserRepositoryCustom {

    @Autowired
    ElasticsearchTemplate elasticsearchTemplate;

    @Override
    public Page<User> search(String query) {
        NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder();
        nativeSearchQueryBuilder.withQuery(QueryBuilders.multiMatchQuery(query, SearchField.FIRST_NAME, SearchField.LAST_NAME, SearchField.BIO_DATA, SearchField.ADMIN_JOB_RESPONSIBILITY));
        nativeSearchQueryBuilder.withSort(SortBuilders.fieldSort(SearchField.FIRST_NAME).order(SortOrder.ASC));
        SearchQuery searchQuery = nativeSearchQueryBuilder.build();
        Page<User> userPage = elasticsearchTemplate.queryForPage(searchQuery, User.class);
        return userPage;
    }

    @Override
    public List<String> getUserNameSuggestions(String query) {
        CompletionSuggestionBuilder suggestionsBuilder = new CompletionSuggestionBuilder(SearchField.NAME_SUGGEST);
        suggestionsBuilder.text(query);
        suggestionsBuilder.field(SearchField.SEARCH_SUGGEST);
        SuggestRequestBuilder suggestRequestBuilder =elasticsearchTemplate.getClient().prepareSuggest(SearchField.INDEX_NAME).addSuggestion(suggestionsBuilder);
        SuggestResponse suggestResponse = suggestRequestBuilder.execute().actionGet();
        Iterator<? extends Suggest.Suggestion.Entry.Option> iterator = null;
        if(suggestResponse.getSuggest().getSuggestion(SearchField.NAME_SUGGEST)!=null) {
            iterator = suggestResponse.getSuggest().getSuggestion(SearchField.NAME_SUGGEST).iterator().next().getOptions().iterator();
        }

        List<String> items = new ArrayList<>();

        if(iterator != null) {
            while (iterator.hasNext()) {
                Suggest.Suggestion.Entry.Option next = iterator.next();
                items.add(next.getText().string());
            }
        }

        return items;

    }
}
