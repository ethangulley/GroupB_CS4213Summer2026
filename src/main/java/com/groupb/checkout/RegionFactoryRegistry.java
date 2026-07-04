package com.groupb.checkout;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class RegionFactoryRegistry {
    private final List<RegionFactory> factories = new ArrayList<>();

    RegionFactoryRegistry() {
        factories.add(new UnitedStatesFactory());
        factories.add(new UnitedKingdomFactory());
    }

    List<RegionFactory> factories() {
        return Collections.unmodifiableList(factories);
    }

    void addFactory(RegionFactory factory) {
        factories.add(factory);
    }

    boolean containsFactoryName(String factoryName) {
        for (RegionFactory factory : factories) {
            if (factory.factoryName().equalsIgnoreCase(factoryName)) {
                return true;
            }
        }

        return false;
    }

    RegionFactory findByMenuChoice(String choice) {
        try {
            int index = Integer.parseInt(choice) - 1;

            if (index >= 0 && index < factories.size()) {
                return factories.get(index);
            }
        } catch (NumberFormatException ignored) {
        }

        return null;
    }
}
