package com.yapp.cvs.domain.enums


enum class OAuthLoginType(
    val registrationId: String,
): MemberParser {
    GOOGLE("google") {
        override fun parseMember(attr: Map<String, Any>, nickName: String): OAuthMember {
            return OAuthMember.google(attr)
        }
    },
    ;

    companion object {
        fun getByRegistrationId(registrationId: String): OAuthLoginType {
            return values().first { it.registrationId == registrationId }
        }
    }
}

data class OAuthMember(
    val email: String,
    val loginType: OAuthLoginType,
){
  companion object {
      fun google(attributes: Map<String, Any?>): OAuthMember{
          return OAuthMember(
              email = attributes["email"].toString(),
              loginType = OAuthLoginType.GOOGLE
          )
      }
  }
}

interface MemberParser {
    fun parseMember(attr: Map<String, Any>, nickName: String): OAuthMember
}