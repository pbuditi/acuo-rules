package com.acuo.rules.eligibility

import com.acuo.common.model.agreements.Agreement
import com.acuo.common.model.assets.Assets
import org.kie.api.KieServices
import org.kie.api.runtime.KieSession
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import spock.lang.Specification

class rule_us_FXHaircut_im_a_spec extends Specification {
    String ksessionName = "EligibilityKS"

    KieSession ksession
    Logger ruleLogger

    def setup() {
        def ks = KieServices.Factory.get()
        def kContainer = ks.getKieClasspathContainer()
        ksession = kContainer.newKieSession(ksessionName)

        ruleLogger = LoggerFactory.getLogger(ksessionName)
        ksession.setGlobal("log", ruleLogger)
    }
    def "FX Haircut rule us_im_a"() {
        when: "add an asset"
        def asset = new LocalAsset(currency: "SGD", id: "usima")
        def agreement = new LocalAgreement(marginType: "Initial",terminateCurrency: "USD", settlementCurrency: "JPY")
        def regime = new Regime(name:"US")
        ksession.insert(asset)
        ksession.insert(regime)
        ksession.insert(agreement)

        and: "we fire all rules"
        ksession.fireAllRules()

        then: "then we get rules regime and class"
        asset.FXHaircut == 0.08
    }
}