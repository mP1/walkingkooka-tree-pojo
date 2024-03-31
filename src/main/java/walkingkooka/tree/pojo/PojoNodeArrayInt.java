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

import java.util.Arrays;
import java.util.List;

/**
 * A {@link walkingkooka.tree.Node} where each child is an element in the original {@link int[]}.
 */
@SuppressWarnings("lgtm[java/inconsistent-equals-and-hashcode]")
final class PojoNodeArrayInt extends PojoNodeArray {

    static PojoNodeArrayInt with(final PojoName name,
                                 final int[] value,
                                 final int index,
                                 final PojoNodeContext context) {
        return new PojoNodeArrayInt(name, value, index, context);
    }

    private PojoNodeArrayInt(final PojoName name,
                             final int[] value,
                             final int index,
                             final PojoNodeContext context) {
        super(name, value, index, context);
    }

    private int[] valueAsIntArray() {
        return Cast.to(this.value);
    }

    // children ..................................................................................

    @Override //
    PojoNode replaceChildren(final List<PojoNode> children) {
        final int[] newChildren = new int[children.size()];

        int i = 0;
        for (PojoNode child : children) {
            newChildren[i] = (int) child.value();
            i++;
        }

        return this.replace(newChildren);
    }

    @Override //
    PojoNode replaceChild(final PojoNode newChild) {
        final int[] newChildren = new int[this.childrenCount()];

        newChildren[newChild.index()] = (int) newChild.value();

        return this.replace(newChildren);
    }

    @Override //
    PojoNode replaceChildrenValues(final List<Object> values) {
        final int[] newChildren = new int[values.size()];

        int i = 0;
        for (Object child : values) {
            newChildren[i] = (int) child;
            i++;
        }

        return this.replace(newChildren);
    }

    private PojoNode replace(final int[] values) {
        return new PojoNodeArrayInt(this.name(),
                values,
                this.index(),
                this.context)
                .replaceChild(this.parent());
    }

    @Override
    Object elementValue(final int index) {
        return this.valueAsIntArray()[index];
    }

    @Override int childrenCount() {
        return this.valueAsIntArray().length;
    }

    // Object...........................................................................................................

    @Override
    public int hashCode() {
        return Arrays.hashCode(this.valueAsIntArray());
    }

    @Override
    boolean equals0(final PojoNode other) {
        final PojoNodeArrayInt otherArray = Cast.to(other);
        return Arrays.equals(this.valueAsIntArray(), otherArray.valueAsIntArray());
    }

    @Override
    public String toString() {
        return Arrays.toString(this.valueAsIntArray());
    }
}
