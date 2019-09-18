package com.software.zone.pro.eschool.config;

import java.time.Duration;

import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;

import io.github.jhipster.config.jcache.BeanClassLoaderAwareJCacheRegionFactory;
import io.github.jhipster.config.JHipsterProperties;

import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.serviceregistry.Registration;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
public class CacheConfiguration {

    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        BeanClassLoaderAwareJCacheRegionFactory.setBeanClassLoader(this.getClass().getClassLoader());
        JHipsterProperties.Cache.Ehcache ehcache =
            jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                .build());
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            cm.createCache(com.software.zone.pro.eschool.repository.UserRepository.USERS_BY_LOGIN_CACHE, jcacheConfiguration);
            cm.createCache(com.software.zone.pro.eschool.repository.UserRepository.USERS_BY_EMAIL_CACHE, jcacheConfiguration);
            cm.createCache(com.software.zone.pro.eschool.domain.User.class.getName(), jcacheConfiguration);
            cm.createCache(com.software.zone.pro.eschool.domain.Authority.class.getName(), jcacheConfiguration);
            cm.createCache(com.software.zone.pro.eschool.domain.User.class.getName() + ".authorities", jcacheConfiguration);
            cm.createCache(com.software.zone.pro.eschool.domain.Category.class.getName(), jcacheConfiguration);
            cm.createCache(com.software.zone.pro.eschool.domain.Abscence.class.getName(), jcacheConfiguration);
            cm.createCache(com.software.zone.pro.eschool.domain.Classroom.class.getName(), jcacheConfiguration);
            cm.createCache(com.software.zone.pro.eschool.domain.ClassroomHistory.class.getName(), jcacheConfiguration);
            cm.createCache(com.software.zone.pro.eschool.domain.Exam.class.getName(), jcacheConfiguration);
            cm.createCache(com.software.zone.pro.eschool.domain.ExamResult.class.getName(), jcacheConfiguration);
            cm.createCache(com.software.zone.pro.eschool.domain.Homework.class.getName(), jcacheConfiguration);
            cm.createCache(com.software.zone.pro.eschool.domain.Job.class.getName(), jcacheConfiguration);
            cm.createCache(com.software.zone.pro.eschool.domain.JobHistory.class.getName(), jcacheConfiguration);
            cm.createCache(com.software.zone.pro.eschool.domain.Lesson.class.getName(), jcacheConfiguration);
            cm.createCache(com.software.zone.pro.eschool.domain.LessonHistory.class.getName(), jcacheConfiguration);
            cm.createCache(com.software.zone.pro.eschool.domain.LessonHistoryPreparation.class.getName(), jcacheConfiguration);
            cm.createCache(com.software.zone.pro.eschool.domain.Message.class.getName(), jcacheConfiguration);
            cm.createCache(com.software.zone.pro.eschool.domain.Payment.class.getName(), jcacheConfiguration);
            cm.createCache(com.software.zone.pro.eschool.domain.School.class.getName(), jcacheConfiguration);
            cm.createCache(com.software.zone.pro.eschool.domain.SchoolAuthority.class.getName(), jcacheConfiguration);
            cm.createCache(com.software.zone.pro.eschool.domain.StudentHomework.class.getName(), jcacheConfiguration);
            cm.createCache(com.software.zone.pro.eschool.domain.TimeSheet.class.getName(), jcacheConfiguration);
            cm.createCache(com.software.zone.pro.eschool.domain.TimeSlot.class.getName(), jcacheConfiguration);
            cm.createCache(com.software.zone.pro.eschool.domain.TimeSlotHistory.class.getName(), jcacheConfiguration);
            // jhipster-needle-ehcache-add-entry
        };
    }
}
