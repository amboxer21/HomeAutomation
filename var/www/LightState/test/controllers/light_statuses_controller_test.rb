require 'test_helper'

class LightStatusesControllerTest < ActionController::TestCase
  setup do
    @light_status = light_statuses(:one)
  end

  test "should get index" do
    get :index
    assert_response :success
    assert_not_nil assigns(:light_statuses)
  end

  test "should get new" do
    get :new
    assert_response :success
  end

  test "should create light_status" do
    assert_difference('LightStatus.count') do
      post :create, light_status: { state: @light_status.state }
    end

    assert_redirected_to light_status_path(assigns(:light_status))
  end

  test "should show light_status" do
    get :show, id: @light_status
    assert_response :success
  end

  test "should get edit" do
    get :edit, id: @light_status
    assert_response :success
  end

  test "should update light_status" do
    patch :update, id: @light_status, light_status: { state: @light_status.state }
    assert_redirected_to light_status_path(assigns(:light_status))
  end

  test "should destroy light_status" do
    assert_difference('LightStatus.count', -1) do
      delete :destroy, id: @light_status
    end

    assert_redirected_to light_statuses_path
  end
end
