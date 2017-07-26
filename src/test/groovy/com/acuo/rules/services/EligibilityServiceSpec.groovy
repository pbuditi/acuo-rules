package com.acuo.rules.services

import com.acuo.rules.eligibility.Eligible
import com.acuo.rules.eligibility.HaircutProvider
import com.acuo.rules.eligibility.LocalAsset
import com.acuo.rules.eligibility.RuleList
import com.google.common.collect.ImmutableList
import spock.guice.UseModules
import spock.lang.Specification

import javax.inject.Inject
import javax.inject.Named

@UseModules(RulesModule)
class EligibilityServiceSpec extends Specification {

    @Inject
    @Named("eligibility")
    RulesService service

    def "eligible"() {
        when:
        LocalAsset asset = new LocalAsset()
        asset.setType("cash")
        asset.setId("a1")
        Eligible eligible = new Eligible()
        HaircutProvider provider = new HaircutProvider()
        provider.setName("name:EEA")
        RuleList ruleList = new RuleList()

        service.fireRules(ImmutableList.of(asset, eligible, provider, ruleList))

        then:
        eligible != null
    }

}