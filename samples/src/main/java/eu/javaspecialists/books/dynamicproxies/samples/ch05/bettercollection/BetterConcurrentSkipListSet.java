/*
 * Copyright (C) 2020 Heinz Max Kabutz
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

package eu.javaspecialists.books.dynamicproxies.samples.ch05.bettercollection;

import java.util.concurrent.*;

// tag::listing[]
public class BetterConcurrentSkipListSet<E>
    extends ConcurrentSkipListSet<E>
    implements BetterCollection<E> {
  private final E[] seedArray;
  public BetterConcurrentSkipListSet(E[] seedArray) {
    if (seedArray.length != 0)
      throw new IllegalArgumentException(
          "seedArray must be empty");
    this.seedArray = seedArray;
  }
  @Override
  public E[] toArray() {
    return toArray(seedArray);
  }
  @Override
  public String toString() {
    return "--" + super.toString() + "--";
  }
}
// end::listing[]