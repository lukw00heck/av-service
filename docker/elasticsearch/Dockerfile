FROM docker.elastic.co/elasticsearch/elasticsearch:5.4.1
MAINTAINER dvoraka <alen.dvorak@gmail.com>

ENV ES_JAVA_OPTS="-Xms512m -Xmx512m"

COPY conf/elasticsearch.yml /usr/share/elasticsearch/config/

EXPOSE 9200

CMD ["elasticsearch"]
