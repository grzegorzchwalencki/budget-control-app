package com.MyApp.budgetControl.report;

import com.MyApp.budgetControl.domain.user.UserEntity;
import java.time.Instant;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportJpaRepository extends ReportRepository, JpaRepository<UserEntity, String> {

  MonthlyExpenseReportDTO getMonthlyTotalSummaryForUser(String userId, Instant start, Instant end);

  List<CategoryTotalDTO> getMonthlyCategoriesSummaryforUser(String userId, Instant start, Instant end);
}
