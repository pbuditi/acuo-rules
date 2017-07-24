package com.acuo.rules.eligibility

import lombok.Data

@Data
class LocalAsset {
    String id
    String type
    Integer CQS
    String datascopeAssetType
    String currency
    String idType
    String name
    String ICADCode
    String ticker
    String fitchRating
    String moodyRating
    String creditPrincipalCode
    String convertibleTypeCode
    String issuer
    String index
    String exchange
    String rateType
    double maturityYears = -1
    double setUsClassType
    double parValue
    double minUnit
    double internalCost
    double opptCost
    double availableQuantities
    double notional
    Boolean convertibleFlag
}
