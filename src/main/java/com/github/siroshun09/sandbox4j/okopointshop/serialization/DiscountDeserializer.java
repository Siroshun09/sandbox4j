package com.github.siroshun09.sandbox4j.okopointshop.serialization;

import com.github.siroshun09.configapi.api.Configuration;
import com.github.siroshun09.sandbox4j.okopointshop.model.discount.Discount;
import com.github.siroshun09.sandbox4j.okopointshop.model.discount.Discounter;
import com.github.siroshun09.sandbox4j.okopointshop.model.discount.Period;
import com.github.siroshun09.sandbox4j.okopointshop.util.EmptyStringChecker;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public class DiscountDeserializer implements Deserializer<Configuration, Discount> {

    private final FindingTypeDeserializer<Period> periodDeserializer;
    private final FindingTypeDeserializer<Discounter> discounterDeserializer;

    public DiscountDeserializer(@NotNull FindingTypeDeserializer<Period> periodDeserializer,
                                @NotNull FindingTypeDeserializer<Discounter> discounterDeserializer) {
        this.periodDeserializer = periodDeserializer;
        this.discounterDeserializer = discounterDeserializer;
    }

    @Override
    public @NotNull Discount deserialize(@NotNull Configuration source) {
        var id = EmptyStringChecker.isDefined(source.getString("id"), "id");

        var periodSection = source.getSection("period");
        Period period;

        if (periodSection != null) {
            period = periodDeserializer.deserialize(periodSection);
        } else {
            period = Period.ALWAYS;
        }

        var targets = Set.copyOf(source.getStringList("targets"));

        var details = source.getList("details", Deserializer.CONFIGURATION.andThen(discounterDeserializer).toConfigAPI());

        return new Discount(id, period, targets, details);
    }
}
