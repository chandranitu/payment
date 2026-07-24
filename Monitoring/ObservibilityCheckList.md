# check payment application config file. Application.properties

# check pom file for dependency prometheus. Add dependency

# Add in Application.properties (payment src)
management.endpoints.web.exposure.include=health,info,prometheus
management.endpoint.prometheus.enabled=true
management.prometheus.metrics.export.enabled=true


# prometheus yml file while docker compose up

# mongo DB user creation for payment application. testUser

# check node exporter   http://localhost:9100/metrics

# check into prometheus    http://localhost:9090
process_uptime_seconds
http_server_requests_seconds_count
http_server_requests_seconds_sum
rate(http_server_requests_seconds_count[1m])
jvm_memory_used_bytes
jvm_memory_max_bytes
jvm_memory_used_bytes{area="heap"}
jvm_threads_live_threads
process_cpu_usage
system_cpu_usage
jvm_gc_pause_seconds_count
node_filesystem_avail_bytes

# grafana login 
http://localhost:3000/login
or 
http://172.25.0.5:3000/login    container IP

#Add data source
Connections → Data sources

# Add Import a Node Exporter Dashboard
Dashboards-Click Import
