/*
 * Copyright (C) 2000-2019 Heinz Max Kabutz
 *
 * See the NOTICE file distributed with this work for additional
 * information regarding copyright ownership.  Heinz Max Kabutz
 * licenses this file to you under the Apache License, Version
 * 2.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the
 * License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied.  See the License for the specific
 * language governing permissions and limitations under the
 * License.
 */

package eu.javaspecialists.books.dynamicproxies;

import eu.javaspecialists.books.dynamicproxies.ch03.logging.*;
import eu.javaspecialists.books.dynamicproxies.ch03.protection.*;
import eu.javaspecialists.books.dynamicproxies.ch03.virtual.*;
import eu.javaspecialists.books.dynamicproxies.ch04.*;
import eu.javaspecialists.books.dynamicproxies.ch05.*;
import eu.javaspecialists.books.dynamicproxies.ch06.*;
import eu.javaspecialists.books.dynamicproxies.util.*;

import java.lang.reflect.*;
import java.util.*;
import java.util.function.*;
import java.util.logging.*;

/**
 * Facade for all our dynamic proxies and related pattern
 * implementations.
 */
public class Proxies {
  private Proxies() {}

  // tag::castProxy()[]
  @SuppressWarnings("unchecked")
  public static <P> P castProxy(Class<? super P> clazz,
                                InvocationHandler h) {
    return MethodTurboBooster.boost((P) Proxy.newProxyInstance(
        clazz.getClassLoader(), new Class<?>[] {clazz}, h
    ));
  }
  // end::castProxy()[]

  // tag::simpleProxy()[]
  public static <P> P simpleProxy(Class<? super P> clazz, P p) {
    return castProxy(clazz,
        (proxy, method, args) -> method.invoke(p, args)
    );
  }
  // end::simpleProxy()[]

  // tag::loggingProxy()[]
  public static <P> P loggingProxy(
      Class<? super P> proxiedInterface,
      P p, Logger log) {
    Objects.requireNonNull(p, "p==null");
    return castProxy(proxiedInterface,
        new LoggingInvocationHandler(log, p));
  }
  // end::loggingProxy()[]

  // tag::virtualProxy()[]
  public static <P> P virtualProxy(
      Class<? super P> proxiedInterface,
      Supplier<? extends P> supplier) {
    Objects.requireNonNull(supplier, "supplier==null");
    return castProxy(proxiedInterface,
        new VirtualProxyHandler<>(supplier));
  }
  // end::virtualProxy()[]

  // tag::synchronizedProxy()[]
  public static <P> P synchronizedProxy(
      Class<? super P> proxiedInterface, P p) {
    Objects.requireNonNull(p, "p==null");
    return castProxy(proxiedInterface,
        new SynchronizedHandler<>(p));
  }
  // end::synchronizedProxy()[]

  // tag::dynamicFilter()[]
  public static <P> P filter(
      Class<? super P> filter, Object component) {
    Objects.requireNonNull(component, "component==null");
    return castProxy(filter,
        new FilterHandler(filter, component));
  }
  // end::dynamicFilter()[]

  // tag::adapt()[]
  public static <E> E adapt(Class<? super E> target,
                            Object adaptee,
                            Object adapter) {
    Objects.requireNonNull(adaptee, "adaptee==null");
    Objects.requireNonNull(adapter, "adapter==null");
    return castProxy(target,
        new ObjectAdapterHandler(target, adaptee, adapter));
  }
  // end::adapt()[]

  // tag::compose()[]
  public static <E extends Composite<? super E>> E compose(
      Class<E> target) {
    return compose(target, null);
  }
  public static <E extends Composite<? super E>> E compose(
      Class<E> target, Map<MethodKey, Reducer> mergers) {
    return castProxy(target,
        new CompositeHandler(target, mergers));
  }
  // end::compose()[]
}