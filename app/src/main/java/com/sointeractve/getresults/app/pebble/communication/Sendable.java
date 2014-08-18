package com.sointeractve.getresults.app.pebble.communication;

import com.sointeractve.getresults.app.pebble.responses.ResponseItem;

import java.util.Collection;

interface Sendable {
    public Collection<ResponseItem> getSendable(final int query);
}
