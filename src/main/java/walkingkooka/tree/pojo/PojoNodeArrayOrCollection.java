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

import java.util.List;

/**
 * A {@link walkingkooka.tree.Node} where each child is an element in the original {@link java.util.Collection}.
 */
abstract class PojoNodeArrayOrCollection extends PojoNode2 {

    PojoNodeArrayOrCollection(final PojoName name,
                              final Object value,
                              final int index,
                              final PojoNodeContext context) {
        super(name, value, index, context);
    }

    // children ..................................................................................

    @Override final PojoNodeArrayOrCollectionChildrenList<?> createChildrenList() {
        return PojoNodeArrayOrCollectionChildrenList.with(this);
    }

    /**
     * Casts / converts the provided value into a {@link List} of objects. For performance reasons if converting,
     * this should be smart somehow, particulary for {@link java.util.Set} and {@link java.util.Map}.
     */
    abstract List<Object> valueAsList();
}
