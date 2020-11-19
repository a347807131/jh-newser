<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('jhdApp.userExt.home.title')" id="user-ext-heading">User Exts</span>
            <router-link :to="{name: 'UserExtCreate'}" tag="button" id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-user-ext">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('jhdApp.userExt.home.createLabel')">
                    Create a new User Ext
                </span>
            </router-link>
        </h2>
        <b-alert :show="dismissCountDown"
            dismissible
            :variant="alertType"
            @dismissed="dismissCountDown=0"
            @dismiss-count-down="countDownChanged">
            {{alertMessage}}
        </b-alert>
        <br/>
        <div class="alert alert-warning" v-if="!isFetching && userExts && userExts.length === 0">
            <span v-text="$t('jhdApp.userExt.home.notFound')">No userExts found</span>
        </div>
        <div class="table-responsive" v-if="userExts && userExts.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th><span v-text="$t('global.field.id')">ID</span></th>
                    <th><span v-text="$t('jhdApp.userExt.pthone')">Pthone</span></th>
                    <th><span v-text="$t('jhdApp.userExt.user')">User</span></th>
                    <th><span v-text="$t('jhdApp.userExt.news')">News</span></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="userExt in userExts"
                    :key="userExt.id">
                    <td>
                        <router-link :to="{name: 'UserExtView', params: {userExtId: userExt.id}}">{{userExt.id}}</router-link>
                    </td>
                    <td>{{userExt.pthone}}</td>
                    <td>
                        {{userExt.user ? userExt.user.email : ''}}
                    </td>
                    <td>
                        <span v-for="(news, i) in userExt.news" :key="news.id">{{i > 0 ? ', ' : ''}}
                            <router-link class="form-control-static" :to="{name: 'NewsView', params: {newsId: news.id}}">{{news.id}}</router-link>
                        </span>
                    </td>
                    <td class="text-right">
                        <div class="btn-group">
                            <router-link :to="{name: 'UserExtView', params: {userExtId: userExt.id}}" tag="button" class="btn btn-info btn-sm details">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                            </router-link>
                            <router-link :to="{name: 'UserExtEdit', params: {userExtId: userExt.id}}"  tag="button" class="btn btn-primary btn-sm edit">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                            </router-link>
                            <b-button v-on:click="prepareRemove(userExt)"
                                   variant="danger"
                                   class="btn btn-sm"
                                   v-b-modal.removeEntity>
                                <font-awesome-icon icon="times"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.delete')">Delete</span>
                            </b-button>
                        </div>
                    </td>
                </tr>
                </tbody>
            </table>
        </div>
        <b-modal ref="removeEntity" id="removeEntity" >
            <span slot="modal-title"><span id="jhdApp.userExt.delete.question" v-text="$t('entity.delete.title')">Confirm delete operation</span></span>
            <div class="modal-body">
                <p id="jhi-delete-userExt-heading" v-text="$t('jhdApp.userExt.delete.question', {'id': removeId})">Are you sure you want to delete this User Ext?</p>
            </div>
            <div slot="modal-footer">
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-userExt" v-text="$t('entity.action.delete')" v-on:click="removeUserExt()">Delete</button>
            </div>
        </b-modal>
    </div>
</template>

<script lang="ts" src="./user-ext.component.ts">
</script>
