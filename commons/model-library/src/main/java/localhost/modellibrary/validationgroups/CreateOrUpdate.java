package localhost.modellibrary.validationgroups;

import jakarta.validation.groups.Default;

/**
 * Validation group for create or update models.
 */
public interface CreateOrUpdate extends Create, Update, Default {

}
