select top 2000 u.UnitName , AggrRSCP from WCDMAActiveSet r
join	Sessions s on s.SessionId = r.SessionId
join	FileList f on f.FileId = s.FileId
join	RC_main_server.dbo.Units u on u.UnitName COLLATE Latin1_General_CI_AS = f.ASideLocation
order by MsgTime DESC