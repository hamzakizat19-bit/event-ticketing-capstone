package kz.kaznaru.capstone;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;
import static com.tngtech.archunit.library.Architectures.layeredArchitecture;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

/** Dependency rules are tests: breaking the architecture breaks the build. */
@AnalyzeClasses(packages = "kz.kaznaru.capstone", importOptions = ImportOption.DoNotIncludeTests.class)
class ArchitectureTest {

    // ---- rules from the course starter repo ----

    @ArchTest
    static final ArchRule domain_depends_on_nothing_else =
        noClasses().that().resideInAPackage("..domain..")
            .should().dependOnClassesThat().resideInAnyPackage("..service..", "..infra..", "..ui..");

    @ArchTest
    static final ArchRule service_does_not_know_infra_or_ui =
        noClasses().that().resideInAPackage("..service..")
            .should().dependOnClassesThat().resideInAnyPackage("..infra..", "..ui..");

    @ArchTest
    static final ArchRule ui_does_not_know_infra =
        noClasses().that().resideInAPackage("..ui..")
            .should().dependOnClassesThat().resideInAPackage("..infra..");

    // ---- our extra rules ----

    /** Whole layer picture: nobody may call ui or infra; only App (outside all layers) wires them. */
    @ArchTest
    static final ArchRule layers = layeredArchitecture()
            .consideringOnlyDependenciesInLayers()
            .withOptionalLayers(true) // layers are added milestone by milestone (M1 has no code yet)
            .layer("Domain").definedBy("kz.kaznaru.capstone.domain..")
            .layer("Service").definedBy("kz.kaznaru.capstone.service..")
            .layer("Infra").definedBy("kz.kaznaru.capstone.infra..")
            .layer("UI").definedBy("kz.kaznaru.capstone.ui..")
            .whereLayer("UI").mayNotBeAccessedByAnyLayer()
            .whereLayer("Infra").mayNotBeAccessedByAnyLayer()
            .whereLayer("Service").mayOnlyBeAccessedByLayers("UI", "Infra")
            .whereLayer("Domain").mayOnlyBeAccessedByLayers("Service", "Infra", "UI");

    /** Business code never prints: output belongs to ui/infra. */
    @ArchTest
    static final ArchRule domain_and_service_do_not_print =
        noClasses().that().resideInAnyPackage("..domain..", "..service..")
            .should().accessField(System.class, "out");
}
