<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="news">
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span
                    v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="news.id" :to="{name: 'NewsEdit', params: {newsId: news.id}}" tag="button"
                             class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span
                    v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
                <dl class="row jh-entity-details">
                    <dt>
                        <h2 v-text="$t('jhdApp.news.title')+':'">Title</h2>
                    </dt>
                    <dd>
                        <h2>{{ news.title }}</h2>
                    </dd>
                    <dt>
                        <span v-text="$t('jhdApp.news.source')">Source</span>
                    </dt>
                    <dd>
                        <span>{{ news.source }}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('jhdApp.news.link')">Link</span>
                    </dt>
                    <dd>
                        <span>{{ news.link }}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('jhdApp.news.kind')">Kind</span>
                    </dt>
                    <dd>
                        <span>{{ news.kind }}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('jhdApp.news.time')">Time</span>
                    </dt>
                    <dd>
                        <span v-if="news.time">{{ $d(Date.parse(news.time), 'long') }}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('jhdApp.news.content')">Content</span>
                    </dt>
                </dl>
                <div>
                    <span v-html="news.content"></span>
                </div>

            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./news-details.component.ts">
</script>
