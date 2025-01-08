package net.focik.Smartgaz.logservice.domain.port.secondary;


import net.focik.Smartgaz.logservice.domain.model.LogEntry;

import java.time.LocalDateTime;
import java.util.List;

public interface LogsRepository {
    List<LogEntry> getLogsByDate(LocalDateTime from, LocalDateTime to);
    List<LogEntry> getTodayLogs();
}
