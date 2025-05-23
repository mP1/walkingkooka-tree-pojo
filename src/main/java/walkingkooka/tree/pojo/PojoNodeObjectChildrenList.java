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

import java.util.Arrays;
import java.util.List;

/**
 * The {@link List} holding all properties in an instance.
 */
final class PojoNodeObjectChildrenList extends PojoNodeChildrenList<PojoNodeObject> {

    /**
     * Lazily wrapped pojo nodes.
     */
    private final PojoNode[] nodes;

    static PojoNodeObjectChildrenList with(final PojoNodeObject parent) {
        return new PojoNodeObjectChildrenList(parent);
    }

    private PojoNodeObjectChildrenList(final PojoNodeObject parent) {
        super(parent);
        this.nodes = new PojoNode[parent.childrenCount()];
    }

    /**
     * Supports caching pojo wrappers for individual elements.
     */
    @Override
    public PojoNode get(final int index) {
        PojoNode node = this.nodes[index];
        if (null == node) {
            node = this.replace(index);
            this.nodes[index] = node;
        }
        return node;
    }

    @Override
    Object elementValue(final int index) {
        return this.property(index).get(this.parent.value());
    }

    @Override
    PojoNode replace(final int index) {
        final PojoProperty property = this.property(index);

        return this.replace0(property.name(),// the name of the property(field)
            property.get(this.parent.value()),
            index);
    }

    private PojoProperty property(final int index) {
        return this.parent.properties().get(index);
    }

    @Override
    boolean isSameType(final Object other) {
        return other instanceof PojoNodeObjectChildrenList;
    }

    @Override //
    void clearChildrenNodeCache() {
        Arrays.fill(this.nodes, null);
    }
}
