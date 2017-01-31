package com.eovalencia.veddit.source.restApi.model;

import com.eovalencia.veddit.model.SubReddit;

import java.util.ArrayList;

/**
 * Created by Eovalencia on 22/01/17.
 */

public class SubRedditResponse {

    private ArrayList<SubReddit> subRedditsResponse;

    public ArrayList<SubReddit> getSubRedditsResponse() {
        return subRedditsResponse;
    }

    public void setSubRedditsResponse(ArrayList<SubReddit> subRedditsResponse) {
        this.subRedditsResponse = subRedditsResponse;
    }
}
