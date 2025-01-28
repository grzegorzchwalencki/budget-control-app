package com.MyApp.budgetControl;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;
import static com.tngtech.archunit.library.dependencies.SlicesRuleDefinition.slices;

@AnalyzeClasses(packagesOf = BudgetControlApplication.class, importOptions = ImportOption.DoNotIncludeTests.class)
public class ArchitectureTest {

  @ArchTest
  public void noCircularDependenciesBetweenPackages(JavaClasses classes) {
    slices()
        .matching("com.MyApp.budgetControl.(*)..")
        .should()
        .beFreeOfCycles()
        .check(classes);
  }

  @ArchTest
  public void repositoriesShouldBeInterfacesAndPackagePrivate(JavaClasses classes) {
    classes()
        .that().haveSimpleNameEndingWith("Repository")
        .should().beInterfaces().andShould().bePackagePrivate()
        .check(classes);
  }

  @ArchTest
  public void servicesUsedOnlyInsideDomainPackage(JavaClasses classes) {
    classes()
        .that().areAnnotatedWith("org.springframework.stereotype.Service")
        .and().resideInAPackage("..domain.(**)..")
        .should().onlyBeAccessed().byClassesThat().resideInAnyPackage("..domain..")
        .check(classes);
  }

  @ArchTest
  public void servicesShouldNotDependsOfEachOther(JavaClasses classes) {
    noClasses()
        .that().areAnnotatedWith("org.springframework.stereotype.Service")
        .and().resideInAPackage("..domain.(**)..")
        .should().dependOnClassesThat().areAnnotatedWith("org.springframework.stereotype.Service")
        .andShould().dependOnClassesThat().resideInAnyPackage("..category..", "..expense..", "..user..")
        .check(classes);
  }
}
