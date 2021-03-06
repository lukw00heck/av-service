package dvoraka.avservice.user.service

import dvoraka.avservice.user.configuration.UserConfig
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.ldap.core.LdapTemplate
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import spock.lang.Specification

/**
 * LDAP service spec.
 */
@ContextConfiguration(classes = [UserConfig.class])
@ActiveProfiles('ldap')
class LdapUserServiceISpec extends Specification {

    @Autowired
    LdapTemplate ldapTemplate


    def "test"() {
        expect:
            true
    }
}
