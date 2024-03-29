/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jclouds.ec2.services;

import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Method;

import org.jclouds.ec2.domain.IpProtocol;
import org.jclouds.ec2.domain.UserIdGroupPair;
import org.jclouds.ec2.xml.DescribeSecurityGroupsResponseHandler;
import org.jclouds.http.HttpRequest;
import org.jclouds.http.functions.ParseSax;
import org.jclouds.http.functions.ReleasePayloadAndReturn;
import org.jclouds.rest.functions.ReturnEmptySetOnNotFoundOr404;
import org.jclouds.rest.functions.ReturnVoidOnNotFoundOr404;
import org.jclouds.rest.internal.RestAnnotationProcessor;
import org.testng.annotations.Test;

import com.google.inject.TypeLiteral;

/**
 * Tests behavior of {@code SecurityGroupAsyncClient}
 * 
 * @author Adrian Cole
 */
// NOTE:without testName, this will not call @Before* and fail w/NPE during surefire
@Test(groups = "unit", testName = "SecurityGroupAsyncClientTest")
public class SecurityGroupAsyncClientTest extends BaseEC2AsyncClientTest<SecurityGroupAsyncClient> {

   public void testDeleteSecurityGroup() throws SecurityException, NoSuchMethodException, IOException {
      Method method = SecurityGroupAsyncClient.class.getMethod("deleteSecurityGroupInRegion", String.class,
            String.class);
      HttpRequest request = processor.createRequest(method, null, "name");

      assertRequestLineEquals(request, "POST https://ec2.us-east-1.amazonaws.com/ HTTP/1.1");
      assertNonPayloadHeadersEqual(request, "Host: ec2.us-east-1.amazonaws.com\n");
      assertPayloadEquals(request, "Action=DeleteSecurityGroup&GroupName=name",
            "application/x-www-form-urlencoded", false);

      assertResponseParserClassEquals(method, request, ReleasePayloadAndReturn.class);
      assertSaxResponseParserClassEquals(method, null);
      assertExceptionParserClassEquals(method, ReturnVoidOnNotFoundOr404.class);

      checkFilters(request);
   }

   HttpRequest createSecurityGroup = HttpRequest.builder().method("POST")
                                                .endpoint("https://ec2.us-east-1.amazonaws.com/")
                                                .addHeader("Host", "ec2.us-east-1.amazonaws.com")
                                                .addFormParam("Action", "CreateSecurityGroup")
                                                .addFormParam("GroupDescription", "description")
                                                .addFormParam("GroupName", "name")
                                                .addFormParam("Signature", "F3o0gnZcX9sWrtDUhVwi3k5GY2JKLP0Dhi6CcEqK2vE%3D")
                                                .addFormParam("SignatureMethod", "HmacSHA256")
                                                .addFormParam("SignatureVersion", "2")
                                                .addFormParam("Timestamp", "2009-11-08T15%3A54%3A08.897Z")
                                                .addFormParam("Version", "2010-06-15")
                                                .addFormParam("AWSAccessKeyId", "identity").build();

   public void testCreateSecurityGroup() throws SecurityException, NoSuchMethodException, IOException {
      Method method = SecurityGroupAsyncClient.class.getMethod("createSecurityGroupInRegion", String.class,
            String.class, String.class);
      HttpRequest request = processor.createRequest(method, null, "name", "description");

      request = request.getFilters().get(0).filter(request);
      
      assertRequestLineEquals(request, "POST https://ec2.us-east-1.amazonaws.com/ HTTP/1.1");
      assertNonPayloadHeadersEqual(request, "Host: ec2.us-east-1.amazonaws.com\n");
      assertPayloadEquals(request, createSecurityGroup.getPayload().getRawContent().toString(),
            "application/x-www-form-urlencoded", false);

      assertResponseParserClassEquals(method, request, ReleasePayloadAndReturn.class);
      assertSaxResponseParserClassEquals(method, null);
      assertExceptionParserClassEquals(method, null);

      checkFilters(request);
   }

   public void testDescribeSecurityGroups() throws SecurityException, NoSuchMethodException, IOException {
      Method method = SecurityGroupAsyncClient.class.getMethod("describeSecurityGroupsInRegion", String.class, Array
            .newInstance(String.class, 0).getClass());
      HttpRequest request = processor.createRequest(method, (String) null);

      assertRequestLineEquals(request, "POST https://ec2.us-east-1.amazonaws.com/ HTTP/1.1");
      assertNonPayloadHeadersEqual(request, "Host: ec2.us-east-1.amazonaws.com\n");
      assertPayloadEquals(request, "Action=DescribeSecurityGroups",
            "application/x-www-form-urlencoded", false);

      assertResponseParserClassEquals(method, request, ParseSax.class);
      assertSaxResponseParserClassEquals(method, DescribeSecurityGroupsResponseHandler.class);
      assertExceptionParserClassEquals(method, ReturnEmptySetOnNotFoundOr404.class);

      checkFilters(request);
   }

   public void testDescribeSecurityGroupsArgs() throws SecurityException, NoSuchMethodException, IOException {
      Method method = SecurityGroupAsyncClient.class.getMethod("describeSecurityGroupsInRegion", String.class, Array
            .newInstance(String.class, 0).getClass());
      HttpRequest request = processor.createRequest(method, null, "1", "2");

      assertRequestLineEquals(request, "POST https://ec2.us-east-1.amazonaws.com/ HTTP/1.1");
      assertNonPayloadHeadersEqual(request, "Host: ec2.us-east-1.amazonaws.com\n");
      assertPayloadEquals(request, "Action=DescribeSecurityGroups&GroupName.1=1&GroupName.2=2",
            "application/x-www-form-urlencoded", false);

      assertResponseParserClassEquals(method, request, ParseSax.class);
      assertSaxResponseParserClassEquals(method, DescribeSecurityGroupsResponseHandler.class);
      assertExceptionParserClassEquals(method, ReturnEmptySetOnNotFoundOr404.class);

      checkFilters(request);
   }

   public void testAuthorizeSecurityGroupIngressGroup() throws SecurityException, NoSuchMethodException, IOException {
      Method method = SecurityGroupAsyncClient.class.getMethod("authorizeSecurityGroupIngressInRegion", String.class,
            String.class, UserIdGroupPair.class);
      HttpRequest request = processor.createRequest(method, null, "group", new UserIdGroupPair("sourceUser",
            "sourceGroup"));

      assertRequestLineEquals(request, "POST https://ec2.us-east-1.amazonaws.com/ HTTP/1.1");
      assertNonPayloadHeadersEqual(request, "Host: ec2.us-east-1.amazonaws.com\n");
      assertPayloadEquals(
            request,
            "Action=AuthorizeSecurityGroupIngress&GroupName=group&SourceSecurityGroupOwnerId=sourceUser&SourceSecurityGroupName=sourceGroup",
            "application/x-www-form-urlencoded", false);

      assertResponseParserClassEquals(method, request, ReleasePayloadAndReturn.class);
      assertSaxResponseParserClassEquals(method, null);
      assertExceptionParserClassEquals(method, null);

      checkFilters(request);
   }

   HttpRequest authorizeSecurityGroupIngressCidr = HttpRequest.builder().method("POST")
                                                              .endpoint("https://ec2.us-east-1.amazonaws.com/")
                                                              .addHeader("Host", "ec2.us-east-1.amazonaws.com")
                                                              .addFormParam("Action", "AuthorizeSecurityGroupIngress")
                                                              .addFormParam("CidrIp", "0.0.0.0/0")
                                                              .addFormParam("FromPort", "6000")
                                                              .addFormParam("GroupName", "group")
                                                              .addFormParam("IpProtocol", "tcp")
                                                              .addFormParam("Signature", "6NQega9YUGDxdwk3Y0Hv71u/lHi%2B0D6qMCJLpJVD/aI%3D")
                                                              .addFormParam("SignatureMethod", "HmacSHA256")
                                                              .addFormParam("SignatureVersion", "2")
                                                              .addFormParam("Timestamp", "2009-11-08T15%3A54%3A08.897Z")
                                                              .addFormParam("ToPort", "7000")
                                                              .addFormParam("Version", "2010-06-15")
                                                              .addFormParam("AWSAccessKeyId", "identity").build();

   public void testAuthorizeSecurityGroupIngressCidr() throws SecurityException, NoSuchMethodException, IOException {
      Method method = SecurityGroupAsyncClient.class.getMethod("authorizeSecurityGroupIngressInRegion", String.class,
            String.class, IpProtocol.class, int.class, int.class, String.class);
      HttpRequest request = processor.createRequest(method, null, "group", IpProtocol.TCP, 6000, 7000, "0.0.0.0/0");

      request = request.getFilters().get(0).filter(request);
      
      assertRequestLineEquals(request, "POST https://ec2.us-east-1.amazonaws.com/ HTTP/1.1");
      assertPayloadEquals(request, authorizeSecurityGroupIngressCidr.getPayload().getRawContent().toString(),
            "application/x-www-form-urlencoded", false);

      assertResponseParserClassEquals(method, request, ReleasePayloadAndReturn.class);
      assertSaxResponseParserClassEquals(method, null);
      assertExceptionParserClassEquals(method, null);

      checkFilters(request);
   }

   public void testRevokeSecurityGroupIngressGroup() throws SecurityException, NoSuchMethodException, IOException {
      Method method = SecurityGroupAsyncClient.class.getMethod("revokeSecurityGroupIngressInRegion", String.class,
            String.class, UserIdGroupPair.class);
      HttpRequest request = processor.createRequest(method, null, "group", new UserIdGroupPair("sourceUser",
            "sourceGroup"));

      assertRequestLineEquals(request, "POST https://ec2.us-east-1.amazonaws.com/ HTTP/1.1");
      assertNonPayloadHeadersEqual(request, "Host: ec2.us-east-1.amazonaws.com\n");
      assertPayloadEquals(
            request,
            "Action=RevokeSecurityGroupIngress&GroupName=group&SourceSecurityGroupOwnerId=sourceUser&SourceSecurityGroupName=sourceGroup",
            "application/x-www-form-urlencoded", false);

      assertResponseParserClassEquals(method, request, ReleasePayloadAndReturn.class);
      assertSaxResponseParserClassEquals(method, null);
      assertExceptionParserClassEquals(method, null);

      checkFilters(request);
   }

   HttpRequest revokeSecurityGroupIngressCidr = HttpRequest.builder().method("POST")
                                                           .endpoint("https://ec2.us-east-1.amazonaws.com/")
                                                           .addHeader("Host", "ec2.us-east-1.amazonaws.com")
                                                           .addFormParam("Action", "RevokeSecurityGroupIngress")
                                                           .addFormParam("CidrIp", "0.0.0.0/0")
                                                           .addFormParam("FromPort", "6000")
                                                           .addFormParam("GroupName", "group")
                                                           .addFormParam("IpProtocol", "tcp")
                                                           .addFormParam("Signature", "WPlDYXI8P6Ip4F2JIEP3lWrVlP/7gxbZvlshKYlrvxk%3D")
                                                           .addFormParam("SignatureMethod", "HmacSHA256")
                                                           .addFormParam("SignatureVersion", "2")
                                                           .addFormParam("Timestamp", "2009-11-08T15%3A54%3A08.897Z")
                                                           .addFormParam("ToPort", "7000")
                                                           .addFormParam("Version", "2010-06-15")
                                                           .addFormParam("AWSAccessKeyId", "identity").build();

   public void testRevokeSecurityGroupIngressCidr() throws SecurityException, NoSuchMethodException, IOException {
      Method method = SecurityGroupAsyncClient.class.getMethod("revokeSecurityGroupIngressInRegion", String.class,
            String.class, IpProtocol.class, int.class, int.class, String.class);
      HttpRequest request = processor.createRequest(method, null, "group", IpProtocol.TCP, 6000, 7000, "0.0.0.0/0");

      request = request.getFilters().get(0).filter(request);
      
      assertRequestLineEquals(request, "POST https://ec2.us-east-1.amazonaws.com/ HTTP/1.1");
      assertNonPayloadHeadersEqual(request, "Host: ec2.us-east-1.amazonaws.com\n");
      assertPayloadEquals(request, revokeSecurityGroupIngressCidr.getPayload().getRawContent().toString(),
            "application/x-www-form-urlencoded", false);

      assertResponseParserClassEquals(method, request, ReleasePayloadAndReturn.class);
      assertSaxResponseParserClassEquals(method, null);
      assertExceptionParserClassEquals(method, null);

      checkFilters(request);
   }

   @Override
   protected TypeLiteral<RestAnnotationProcessor<SecurityGroupAsyncClient>> createTypeLiteral() {
      return new TypeLiteral<RestAnnotationProcessor<SecurityGroupAsyncClient>>() {
      };
   }

}
