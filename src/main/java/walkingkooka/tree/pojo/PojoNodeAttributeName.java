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

import walkingkooka.naming.Name;
import walkingkooka.text.CaseSensitivity;
import walkingkooka.text.CharSequences;

import java.util.Comparator;
import java.util.Objects;

/**
 * The name of a pojo node attribute.
 */
public final class PojoNodeAttributeName implements Name,
    Comparable<PojoNodeAttributeName> {

    public final static PojoNodeAttributeName CLASS = new PojoNodeAttributeName("class");

    private PojoNodeAttributeName(final String name) {
        super();
        this.name = name;
    }

    // Name.............................................................................................................

    @Override
    public PojoNodeAttributeName name() {
        return this;
    }

    // HasValue.........................................................................................................

    @Override
    public String value() {
        return this.name;
    }

    // Comparable.......................................................................................................

    @Override
    public int compareTo(final PojoNodeAttributeName other) {
        return CASE_SENSITIVITY.comparator()
            .compare(
                this.name,
                other.name
            );
    }

    // toString.........................................................................................................

    @Override
    public String toString() {
        return this.name;
    }

    private final String name;

    // HasCaseSensitivity................................................................................................

    @Override
    public CaseSensitivity caseSensitivity() {
        return CASE_SENSITIVITY;
    }

    private final static CaseSensitivity CASE_SENSITIVITY = CaseSensitivity.SENSITIVE;

    // valueOf..........................................................................................................

    public static PojoNodeAttributeName valueOf(final String name) {
        Objects.requireNonNull(name, "name");

        if(CLASS.name.equals(name)) {
            return CLASS;
        }
        throw new IllegalArgumentException("Unknown attribute name " + CharSequences.quote(name));
    }
}
