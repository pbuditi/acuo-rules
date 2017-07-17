package com.acuo.rules.eligibility

import org.kie.api.KieServices
import org.kie.api.runtime.KieSession
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import spock.lang.Specification

class rule_fitch_bond_hc_spec extends Specification {
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

    def "An Australian Bond will be applied 0.795 haircut."() {
        when: "add a bond asset"
        def asset = new LocalAsset(type: "bond", id: "a1",currency:"AUD")
        def agreement = new LocalAgreement(id: "ag1", baseCurrency: "GBP", majorCurrency: "EUR,USD,GBP")
        def haircutProvider = new HaircutProvider(name: "Fitch")
        def eligible = new Eligible(securityAR: 1)
        ksession.insert(asset)
        ksession.insert(agreement)
        ksession.insert(eligible)
        ksession.insert(haircutProvider)

        and: "we fire all rules"
        ksession.getAgenda().getAgendaGroup( "FitchHaircut" ).setFocus();
        ksession.fireAllRules()

        then: "then we get rules regime and class"
        eligible.securityAR == 1
        eligible.FXAR == 0.795
        eligible.fxHaircut == 0
        Math.round(eligible.haircut*10000)/10000 == 1-0.795
    }
}