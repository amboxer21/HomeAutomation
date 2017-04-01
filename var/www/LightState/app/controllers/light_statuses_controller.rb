class LightStatusesController < ActionController::Base

  skip_before_action :very_authenticity_token

  def index
    @light_status = LightStatus.new
  end

  def new
    @light_status = LightStatus.all
  end

  def create

    @ip    = params[:ip]
    @state = params[:state]

    if !LightStatus.find_by(ip: @ip).nil? 
      @light_status = LightStatus.find_by(ip: @ip)
      LightStatus.all.each do |s| 
        s.update_columns(state: @state)
      end
    #elsif LightStatus.find_by(ip: @ip).nil? && !@state.nil?
    #  LightStatus.all.each do |s|
    #    return unless s.ip.count > 0
    #    `bin/client #{ls.ip} 8080 "#{@state}"`
    #  end
    else
      LightStatus.create(state: @state, ip: @ip)
      @light_status = LightStatus.find_by(ip: @ip)
    end

    if @state == "1"
      Rails.logger.debug "debugging - state = 1"
      `bin/client 192.168.1.177 9486 "onn"`
      LightStatus.all.each do |ls|
        `bin/client #{ls.ip} 8080 "onn"` if is_alive?(ls.ip)
      end
    else
      Rails.logger.debug "debugging - state = 0"
      `bin/client 192.168.1.177 9486 "off"`
      LightStatus.all.each do |ls|
        `bin/client #{ls.ip} 8080 "off"` if is_alive?(ls.ip)
      end
    end

    respond_to do |format|
      if @light_status.save
        format.json { render nothing: true, status: :created }
      else
        format.json { render json: @light_status.errors, status: :unprocessable_entity }
      end
    end
  end

  def is_alive?(ip)
    return `ping -w1 -c1 #{ip}`.match(/, 0% packet loss/) ? true : false
  end

  private
  def lights_status_params
    params.require(:light_status).permit(:id, :state)
  end

end
