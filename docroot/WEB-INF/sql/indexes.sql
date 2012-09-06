create index IX_D92B1638 on BasePortlet_Configuration (propertyname, groupId, companyId);

create index IX_BFC7FF13 on GoogleAnalytics_Configuration (property);
create index IX_6C52A5E on GoogleAnalytics_Configuration (propertyname);
create index IX_2B72309C on GoogleAnalytics_Configuration (propertyname, groupId, companyId);

create index IX_6720F08F on ShowUsersByFilterPortlet_Configuration (propertyname, groupId, companyId);