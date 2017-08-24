#!/bin/sh
# chkconfig: 2345 85 15
# description:Nginx Server
# start-up script for nginx, copy this script to /etc/init.d folder, then it will enable command such as 'service nginx start'

NGINX_HOME=/usr/local/nginx
NGINX_SBIN=$NGINX_HOME/sbin/nginx
NGINX_CONF=$NGINX_HOME/conf/nginx.conf
NGINX_PID=$NGINX_HOME/logs/nginx.pid

NGINX_NAME="Nginx"

. /etc/rc.d/init.d/functions

if [ ! -f $NGINX_SBIN ]
then
    echo "$NGINX_NAME startup: $NGINX_SBIN not exists! "
    exit
fi

start() {
    $NGINX_SBIN -c $NGINX_CONF
    ret=$?
    if [ $ret -eq 0 ]; then
        action $"Starting $NGINX_NAME: " /bin/true
    else
        action $"Starting $NGINX_NAME: " /bin/false
    fi
}

stop() {
    kill `cat $NGINX_PID`
    ret=$?
    if [ $ret -eq 0 ]; then
        action $"Stopping $NGINX_NAME: " /bin/true
    else
        action $"Stopping $NGINX_NAME: " /bin/false
    fi
}

restart() {
    stop
    start
}

check() {
    $NGINX_SBIN -c $NGINX_CONF -t
}


reload() {
    kill -HUP `cat $NGINX_PID` && echo "reload success!"
}

relog() {
    kill -USR1 `cat $NGINX_PID` && echo "relog success!"
}

case "$1" in
    start)
        start
        ;;
    stop)
        stop
        ;;
    restart)
        restart
        ;;
    check|chk)
        check
        ;;
    status)
        status -p $NGINX_PID
        ;;
    reload)
        reload
        ;;
    relog)
        relog
        ;;
    *)
        echo $"Usage: $0 {start|stop|restart|reload|status|check|relog}"
        exit 1
esac