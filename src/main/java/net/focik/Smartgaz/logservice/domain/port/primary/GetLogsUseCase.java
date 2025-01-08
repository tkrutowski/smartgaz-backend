package net.focik.Smartgaz.logservice.domain.port.primary;


import net.focik.Smartgaz.logservice.domain.model.LogEntry;
import net.focik.Smartgaz.logservice.domain.model.LogLevel;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public interface GetLogsUseCase {
    List<LogEntry> getLogs(LocalDateTime from, LocalDateTime to, Set<LogLevel> levels);
    List<LogEntry> getLogs(Set<LogLevel> levels);
}
