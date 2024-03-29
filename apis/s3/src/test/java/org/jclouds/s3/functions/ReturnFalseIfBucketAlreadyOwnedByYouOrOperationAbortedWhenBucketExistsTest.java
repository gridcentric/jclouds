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
package org.jclouds.s3.functions;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;

import org.jclouds.aws.AWSResponseException;
import org.jclouds.aws.domain.AWSError;
import org.jclouds.rest.internal.GeneratedHttpRequest;
import org.jclouds.s3.S3Client;
import org.jclouds.s3.options.PutBucketOptions;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * @author Adrian Cole
 */
@Test(testName = "ReturnFalseIfBucketAlreadyOwnedByYouOrOperationAbortedWhenBucketExistsTest")
public class ReturnFalseIfBucketAlreadyOwnedByYouOrOperationAbortedWhenBucketExistsTest {

   GeneratedHttpRequest putBucket;

   @BeforeClass
   void setUp() throws SecurityException, NoSuchMethodException {
      putBucket = GeneratedHttpRequest.builder()
                                      .method("PUT")
                                      .endpoint("https://adriancole-blobstore113.s3.amazonaws.com/")
                                      .declaring(S3Client.class)
                                      .javaMethod(
                                                S3Client.class.getMethod("putBucketInRegion", String.class, String.class,
                                                         PutBucketOptions[].class))
                                      .args(new Object[] { null, "bucket" }).build();
   }

   @Test
   void testBucketAlreadyOwnedByYouIsOk() throws Exception {
      S3Client client = createMock(S3Client.class);
      replay(client);

      Exception e = getErrorWithCode("BucketAlreadyOwnedByYou");
      assert !new ReturnFalseIfBucketAlreadyOwnedByYouOrOperationAbortedWhenBucketExists(client).setContext(putBucket)
               .apply(e);
      verify(client);
   }

   @Test
   void testOperationAbortedIsOkWhenBucketExists() throws Exception {
      S3Client client = createMock(S3Client.class);
      expect(client.bucketExists("bucket")).andReturn(true);
      replay(client);
      Exception e = getErrorWithCode("OperationAborted");
      assert !new ReturnFalseIfBucketAlreadyOwnedByYouOrOperationAbortedWhenBucketExists(client).setContext(putBucket)
               .apply(e);
      verify(client);
   }

   @Test(expectedExceptions = Exception.class)
   void testOperationAbortedNotOkWhenBucketDoesntExist() throws Exception {
      S3Client client = createMock(S3Client.class);
      expect(client.bucketExists("bucket")).andReturn(false);
      replay(client);
      Exception e = getErrorWithCode("OperationAborted");
      new ReturnFalseIfBucketAlreadyOwnedByYouOrOperationAbortedWhenBucketExists(client).setContext(putBucket).apply(e);
      Assert.fail();
   }

   @Test(expectedExceptions = IllegalStateException.class)
   void testIllegalStateIsNotOk() throws Exception {
      S3Client client = createMock(S3Client.class);
      replay(client);

      Exception e = new IllegalStateException();
      new ReturnFalseIfBucketAlreadyOwnedByYouOrOperationAbortedWhenBucketExists(client).apply(e);
      Assert.fail();

   }

   @Test(expectedExceptions = AWSResponseException.class)
   void testBlahIsNotOk() throws Exception {
      S3Client client = createMock(S3Client.class);
      replay(client);
      Exception e = getErrorWithCode("blah");
      new ReturnFalseIfBucketAlreadyOwnedByYouOrOperationAbortedWhenBucketExists(client).apply(e);
      Assert.fail();
   }

   private Exception getErrorWithCode(String code) {
      AWSError error = new AWSError();
      error.setCode(code);
      return new AWSResponseException(null, null, null, error);
   }
}
