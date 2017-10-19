package com.acuo.rules.eligibility

import groovy.transform.ToString

@ToString(includePackage=false, includeNames=true)
class CLConstraint {
    List<String> assetIds;
    String callId;
    double limitDollar;
    double limitPercent;
    String select;
    int index;
}
