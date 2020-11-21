<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="news">
                <h2 class="jh-entity-heading"><span v-text="$t('jhdApp.news.detail.title')">News</span> {{news.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('jhdApp.news.title')">Title</span>
                    </dt>
                    <dd>
                        <span>{{news.title}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('jhdApp.news.source')">Source</span>
                    </dt>
                    <dd>
                        <span>{{news.source}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('jhdApp.news.link')">Link</span>
                    </dt>
                    <dd>
                        <span>{{news.link}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('jhdApp.news.kind')">Kind</span>
                    </dt>
                    <dd>
                        <span>{{news.kind}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('jhdApp.news.time')">Time</span>
                    </dt>
                    <dd>
                        <span v-if="news.time">{{$d(Date.parse(news.time), 'long') }}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('jhdApp.news.content')">Content</span>
                    </dt>
                </dl>
                <dd>
                    <span v-html="news.content" class="heti--ancient heti--vertical"></span>
                </dd>

                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="news.id" :to="{name: 'NewsEdit', params: {newsId: news.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./news-details.component.ts">
</script>
