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

import walkingkooka.Cast;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

/**
 * A {@link walkingkooka.tree.Node} where each child is an element in the original {@link Object[]}.
 */
@SuppressWarnings("lgtm[java/inconsistent-equals-and-hashcode]")
final class PojoNodeArrayObject extends PojoNodeArray {

    static PojoNodeArrayObject with(final PojoName name,
                                    final Object[] value,
                                    final int index,
                                    final PojoNodeContext context) {
        return new PojoNodeArrayObject(name, value, index, context);
    }

    private PojoNodeArrayObject(final PojoName name,
                                final Object[] value,
                                final int index,
                                final PojoNodeContext context) {
        super(name, value, index, context);
    }

    private Object[] valueAsArray() {
        return Cast.to(this.value);
    }

    // children ..................................................................................

    @Override //
    PojoNode replaceChildren(final List<PojoNode> children) {
        final Object[] newChildren = this.createArray(children.size());

        int i = 0;
        for (PojoNode child : children) {
            newChildren[i] = child.value();
            i++;
        }

        return this.replace(newChildren);
    }

    @Override //
    PojoNode replaceChild(final PojoNode newChild) {
        final Object[] newChildren = this.valueAsArray().clone(); // clone is faster than reflective createArray

        newChildren[newChild.index()] = newChild.value();

        return this.replace(newChildren);
    }

    @Override //
    PojoNode replaceChildrenValues(final List<Object> values) {
        final Object[] newChildren = this.createArray(values.size());

        int i = 0;
        for (Object child : values) {
            newChildren[i] = child;
            i++;
        }

        return this.replace(newChildren);
    }

    private Object[] createArray(final int size) {
        return Cast.to(Array.newInstance(this.value().getClass().getComponentType(), size));
    }

    private PojoNode replace(final Object[] values) {
        return new PojoNodeArrayObject(this.name(),
            values,
            this.index(),
            this.context)
            .replaceChild(this.parent());
    }

    @Override
    Object elementValue(final int index) {
        return this.valueAsArray()[index];
    }

    @Override int childrenCount() {
        return this.valueAsArray().length;
    }

    // Object...........................................................................................................

    @Override
    public int hashCode() {
        return Arrays.hashCode(this.valueAsArray());
    }

    @Override
    boolean equals0(final PojoNode other) {
        final PojoNodeArrayObject otherArray = Cast.to(other);
        return Arrays.equals(this.valueAsArray(), otherArray.valueAsArray());
    }

    @Override
    public String toString() {
        return Arrays.toString(this.valueAsArray());
    }
}
