<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="userExt">
                <h2 class="jh-entity-heading"><span v-text="$t('jhdApp.userExt.detail.title')">UserExt</span> {{userExt.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('jhdApp.userExt.pthone')">Pthone</span>
                    </dt>
                    <dd>
                        <span>{{userExt.pthone}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('jhdApp.userExt.user')">User</span>
                    </dt>
                    <dd>
                        {{userExt.user ? userExt.user.email : ''}}
                    </dd>
                    <dt>
                        <span v-text="$t('jhdApp.userExt.news')">News</span>
                    </dt>
                    <dd>
                        <span v-for="(news, i) in userExt.news" :key="news.id">{{i > 0 ? ', ' : ''}}
                            <router-link :to="{name: 'NewsView', params: {newsId: news.id}}">{{news.title}}</router-link>
                        </span>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="userExt.id" :to="{name: 'UserExtEdit', params: {userExtId: userExt.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./user-ext-details.component.ts">
</script>
