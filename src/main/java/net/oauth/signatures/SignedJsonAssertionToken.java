/**
 * Copyright 2010 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package net.oauth.signatures;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.SignatureException;

import net.oauth.jsontoken.Clock;
import net.oauth.jsontoken.JsonToken;
import net.oauth.jsontoken.crypto.Signer;


/**
 * A signed Json Assertion
 */
public class SignedJsonAssertionToken extends JsonToken {
  
  public static final String JWT = "jwt";
  
  public static final String GRANT_TYPE = "grant_type";
  public static final String GRANT_TYPE_VALUE = "http://oauth.net/grant_type/jwt/1.0/bearer";
  
  // addition JSON token payload fields for signed json assertion
  public static final String SUBJECT = "subject";
  public static final String SCOPE = "scope";
  public static final String NONCE = "nonce";
  
  public SignedJsonAssertionToken(Signer signer, Clock clock) {
    super(signer, clock);
  }

  public SignedJsonAssertionToken(Signer signer) {
    super(signer);
  }
  
  public SignedJsonAssertionToken(JsonToken token) {
    super(token.getPayload());
  }

  public String getSubject() {
    return getParamAsString(SUBJECT);
  }

  public void setSubject(String m) {
    setParam(SUBJECT, m);
  }
  
  public String getScope() {
    return getParamAsString(SCOPE);
  }
  
  public void setScope(String scope) {
    setParam(SCOPE, scope);
  }

  public String getNonce() {
    return getParamAsString(NONCE);
  }

  public void setNonce(String n) {
    setParam(NONCE, n);
  }
  
  public String getJsonAssertionPostBody() throws SignatureException {
    StringBuffer buffer = new StringBuffer();
    buffer.append(GRANT_TYPE).append("=").append(GRANT_TYPE_VALUE);
    buffer.append("&");
    try {
      buffer.append(JWT).append("=").append(serializeAndSign());
      return URLEncoder.encode(buffer.toString(), "UTF-8");
    } catch (UnsupportedEncodingException e) {
      throw new SignatureException("unsupported encoding");
    }
  }

  @Override
  public String serializeAndSign() throws SignatureException {
    return super.serializeAndSign();
  }
  
  @Override
  protected String computeSignatureBaseString() {
    if (getIssuedAt() == null) {
      setIssuedAt(clock.now());
    }
    if (getExpiration() == null) {
      setExpiration(getIssuedAt().plusSeconds(DEFAULT_LIFETIME_IN_MINS * 60));
    }
    return super.computeSignatureBaseString();
  }
}
