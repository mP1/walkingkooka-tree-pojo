/*
 * Copyright 2019 Miroslav Pokorny (github.com/mP1)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package walkingkooka.tree.pojo;

/**
 * A {@link walkingkooka.tree.Node} where each child is an element in the original {@link java.util.Collection}.
 */
@SuppressWarnings("lgtm[java/inconsistent-equals-and-hashcode]")
abstract class PojoNodeCollection extends PojoNodeArrayOrCollection {

    PojoNodeCollection(final PojoName name,
                       final Object value,
                       final int index,
                       final PojoNodeContext context) {
        super(name, value, index, context);
    }

    // Object...........................................................................................................

    @Override
    public final int hashCode() {
        return this.value.hashCode();
    }

    @Override final boolean equals0(final PojoNode other) {
        return this.equals1(other);
    }

    @Override
    public final String toString() {
        return this.toString0();
    }
}
