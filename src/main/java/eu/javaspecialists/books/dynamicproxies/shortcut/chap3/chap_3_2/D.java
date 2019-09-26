/*
 * Copyright (c) 2015. Heinz Max Kabutz , Sven Ruppert
 *   We licenses
 *   this file to you under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License. You may
 *   obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 */

package eu.javaspecialists.books.dynamicproxies.shortcut.chap3.chap_3_2;

import java.util.*;

/**
 * Created by sven on 20.01.15.
 */
public class D implements A {
  private final A a;

  public D(A a) {
    this.a = Objects.requireNonNull(a);
  }

  public boolean equals(Object o) {
        return a.equals(o);
  }

  public int hashCode() {
    return a.hashCode();
  }

}