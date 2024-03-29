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
package org.jclouds.compute.strategy;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.testng.Assert.assertEquals;

import org.jclouds.compute.domain.Image;
import org.jclouds.compute.domain.Template;
import org.jclouds.compute.functions.DefaultCredentialsFromImageOrOverridingCredentials;
import org.jclouds.compute.options.TemplateOptions;
import org.jclouds.domain.Credentials;
import org.jclouds.domain.LoginCredentials;
import org.testng.annotations.Test;

/**
 * 
 * @author Adrian Cole
 */
@Test(groups = "unit", testName = "PrioritizeCredentialsFromTemplateTest")
public class PrioritizeCredentialsFromTemplateTest {
   private static final PrioritizeCredentialsFromTemplate fn = new PrioritizeCredentialsFromTemplate(
         new DefaultCredentialsFromImageOrOverridingCredentials());

   public void testWhenCredentialsNotPresentInImageTemplateOptionsOrParameterReturnNull() {
      Credentials expected = null;

      Image image = createMock(Image.class);
      Template template = createMock(Template.class);

      expect(template.getImage()).andReturn(image);
      expect(image.getDefaultCredentials()).andReturn(null);
      expect(template.getOptions()).andReturn(new TemplateOptions());

      replay(template);
      replay(image);

      assertEquals(fn.apply(template, null), expected);

      verify(template);
      verify(image);

   }

   public void testWhenCredentialsNotPresentInImageTemplateOptionsReturnsFromParameter() {
      LoginCredentials expected = new LoginCredentials("foo", "bar", null, false);

      Image image = createMock(Image.class);
      Template template = createMock(Template.class);

      expect(template.getImage()).andReturn(image);
      expect(image.getDefaultCredentials()).andReturn(null);
      expect(template.getOptions()).andReturn(new TemplateOptions());

      replay(template);
      replay(image);

      assertEquals(fn.apply(template, expected), expected);

      verify(template);
      verify(image);

   }

   public void testWhenCredentialsNotPresentInImageReturnsOneInTemplateOptionsAndNotParameter() {
      LoginCredentials expected = new LoginCredentials("ubuntu", "password", null, false);

      Image image = createMock(Image.class);
      Template template = createMock(Template.class);

      expect(template.getImage()).andReturn(image);
      expect(image.getDefaultCredentials()).andReturn(null);
      expect(template.getOptions()).andReturn(TemplateOptions.Builder.overrideLoginCredentials(expected));

      replay(template);
      replay(image);

      assertEquals(fn.apply(template, new LoginCredentials("foo", "bar", null, false)), expected);

      verify(template);
      verify(image);

   }

   public void testWhenCredentialsNotPresentInImageReturnsCredentialFromTemplateOptionsAndUserFromParameter() {
      Credentials expected = new Credentials("ubuntu", "password");

      Image image = createMock(Image.class);
      Template template = createMock(Template.class);

      expect(template.getImage()).andReturn(image);
      expect(image.getDefaultCredentials()).andReturn(null);
      expect(template.getOptions()).andReturn(TemplateOptions.Builder.overrideLoginUser("ubuntu"));

      replay(template);
      replay(image);

      assertEquals(fn.apply(template, new LoginCredentials("foo", "password", null, false)), expected);

      verify(template);
      verify(image);

   }

   public void testWhenCredentialsNotPresentInTemplateOptionsReturnsOneInImageAndNotParameter() {
      LoginCredentials expected = new LoginCredentials("ubuntu", "password", null, false);

      Image image = createMock(Image.class);
      Template template = createMock(Template.class);

      expect(template.getImage()).andReturn(image);
      expect(image.getDefaultCredentials()).andReturn(expected);
      expect(template.getOptions()).andReturn(new TemplateOptions());

      replay(template);
      replay(image);

      assertEquals(fn.apply(template, new LoginCredentials("foo", "bar", null, false)), expected);

      verify(template);
      verify(image);

   }

   public void testWhenCredentialsPresentInImageOverridesIdentityFromCredentialsInTemplateOptionsAndNotParameter() {
      Credentials expected = new Credentials("ubuntu", "password");

      Image image = createMock(Image.class);
      Template template = createMock(Template.class);

      expect(template.getImage()).andReturn(image);
      expect(image.getDefaultCredentials()).andReturn(new LoginCredentials("user", "password", null, false));
      expect(template.getOptions()).andReturn(TemplateOptions.Builder.overrideLoginUser("ubuntu"));

      replay(template);
      replay(image);

      assertEquals(fn.apply(template, new LoginCredentials("foo", "bar", null, false)), expected);

      verify(template);
      verify(image);

   }

   public void testWhenCredentialsPresentInImageOverridesCredentialFromCredentialsInTemplateOptionsAndNotParameter() {
      LoginCredentials expected = new LoginCredentials("ubuntu", "password", null, false);

      Image image = createMock(Image.class);
      Template template = createMock(Template.class);

      expect(template.getImage()).andReturn(image);
      expect(image.getDefaultCredentials()).andReturn(new LoginCredentials("ubuntu", "password2", null, false));
      expect(template.getOptions()).andReturn(TemplateOptions.Builder.overrideLoginPassword("password"));

      replay(template);
      replay(image);

      assertEquals(fn.apply(template, new LoginCredentials("foo", "bar", null, false)), expected);

      verify(template);
      verify(image);

   }
}
