## Freemarker other setting
Set whether HttpServletRequest attributes are allowed to override (hide) controller generated model attributes of the same name.
spring.freemarker.allow-request-override=
Set whether HttpSession attributes are allowed to override (hide) controller generated model attributes of the same name.
spring.freemarker.allow-session-override=
Enable MVC view resolution for this technology.
spring.freemarker.enabled=
Set whether to expose a RequestContext for use by Spring's macro library, under the name "springMacroRequestContext".
spring.freemarker.expose-spring-macro-helpers=
Prefer file system access for template loading. File system access enables hot detection of template changes.
spring.freemarker.prefer-file-system-access=
spring.freemarker.prefix=# Prefix that gets prepended to view names when building a URL.
spring.freemarker.settings.*=# Well-known FreeMarker keys which will be passed to FreeMarker's Configuration.
spring.freemarker.view-names=# White list of view names that can be resolved.